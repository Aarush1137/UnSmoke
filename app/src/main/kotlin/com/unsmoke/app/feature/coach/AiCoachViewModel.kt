package com.unsmoke.app.feature.coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.unsmoke.app.core.domain.repository.AiInsightsRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.domain.engine.CalculationEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val isError: Boolean = false
)

data class AiCoachUiState(
    val messages: List<ChatMessage> = emptyList(),
    val isLoading: Boolean = true,
    val isTyping: Boolean = false
)

@HiltViewModel
class AiCoachViewModel @Inject constructor(
    private val aiRepo: AiInsightsRepository,
    private val quitAttemptRepo: QuitAttemptRepository,
    private val cravingRepo: CravingRepository,
    private val nrtRepo: NRTRepository,
    private val dataStore: UserPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiCoachUiState())
    val uiState = _uiState.asStateFlow()

    private var chatSession: Chat? = null

    init {
        initializeChat()
    }

    private fun initializeChat() {
        viewModelScope.launch {
            try {
                val attempt = quitAttemptRepo.getActiveAttempt().firstOrNull()
                val userName = dataStore.userName.firstOrNull() ?: "Friend"
                
                var daysFree = 0
                var usesNrt = false
                var cravings = emptyList<com.unsmoke.app.core.data.database.entity.CravingEventEntity>()
                
                if (attempt != null) {
                    daysFree = CalculationEngine.smokeFreeDuration(attempt.startEpochMillis).toDays().toInt()
                    val nrt = nrtRepo.getUsage(attempt.id).firstOrNull() ?: emptyList()
                    usesNrt = nrt.isNotEmpty() || attempt.substanceType == "VAPING"
                    cravings = cravingRepo.getCravings(attempt.id).firstOrNull() ?: emptyList()
                }

                chatSession = aiRepo.startCbtChatSession(
                    userName = userName,
                    cravings = cravings,
                    daysSmokeFree = daysFree,
                    usesNRT = usesNrt
                )

                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        messages = listOf(
                            ChatMessage(text = "Hi $userName. I'm here. What are you feeling right now?", isUser = false)
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false,
                        messages = listOf(ChatMessage(text = "Error connecting to AI Coach. Please check your internet.", isUser = false, isError = true))
                    ) 
                }
            }
        }
    }

    fun sendMessage(text: String) {
        if (text.isBlank() || _uiState.value.isTyping) return
        
        val userMsg = ChatMessage(text = text.trim(), isUser = true)
        _uiState.update { it.copy(
            messages = it.messages + userMsg,
            isTyping = true
        ) }

        viewModelScope.launch {
            try {
                val response = chatSession?.sendMessage(userMsg.text)
                val replyText = response?.text ?: "I'm having trouble thinking right now."
                
                _uiState.update { it.copy(
                    messages = it.messages + ChatMessage(text = replyText, isUser = false),
                    isTyping = false
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    messages = it.messages + ChatMessage(text = "Failed to send message.", isUser = false, isError = true),
                    isTyping = false
                ) }
            }
        }
    }
}