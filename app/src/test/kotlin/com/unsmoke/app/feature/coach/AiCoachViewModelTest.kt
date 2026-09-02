package com.unsmoke.app.feature.coach

import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.type.GenerateContentResponse
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.TextPart
import com.google.common.truth.Truth.assertThat
import com.unsmoke.app.core.domain.repository.AiInsightsRepository
import com.unsmoke.app.core.domain.repository.CravingRepository
import com.unsmoke.app.core.domain.repository.NRTRepository
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore
import com.unsmoke.app.core.util.ErrorManager
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AiCoachViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var aiRepo: AiInsightsRepository
    private lateinit var quitAttemptRepo: QuitAttemptRepository
    private lateinit var cravingRepo: CravingRepository
    private lateinit var nrtRepo: NRTRepository
    private lateinit var dataStore: UserPreferencesDataStore
    private lateinit var errorManager: ErrorManager
    private lateinit var chatSession: Chat
    
    private lateinit var viewModel: AiCoachViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        aiRepo = mockk()
        quitAttemptRepo = mockk()
        cravingRepo = mockk()
        nrtRepo = mockk()
        dataStore = mockk()
        errorManager = mockk(relaxed = true)
        chatSession = mockk()
        
        // Setup default mocks
        every { quitAttemptRepo.getActiveAttempt() } returns flowOf(null)
        every { dataStore.userName } returns flowOf("TestUser")
        
        // Mock the chat session return
        coEvery { 
            aiRepo.startCbtChatSession(any(), any(), any(), any()) 
        } returns chatSession
        
        // Default chat response
        val mockResponse = mockk<GenerateContentResponse>()
        every { mockResponse.text } returns "Here is your CBT technique."
        coEvery { chatSession.sendMessage(any<String>()) } returns mockResponse
        
        viewModel = AiCoachViewModel(
            aiRepo, quitAttemptRepo, cravingRepo, nrtRepo, dataStore, errorManager
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `sendMessage emits User message then Assistant message`() = runTest {
        // Wait for initializeChat to finish
        testScheduler.advanceUntilIdle()
        
        // Assert initial state
        val initialState = viewModel.uiState.value
        assertThat(initialState.messages).isNotEmpty()
        assertThat(initialState.messages[0].text).contains("Hi TestUser")
        assertThat(initialState.isLoading).isFalse()

        // Send a message
        viewModel.sendMessage("I feel stressed")
        
        // Assert typing state
        assertThat(viewModel.uiState.value.isTyping).isTrue()
        assertThat(viewModel.uiState.value.messages.last().text).isEqualTo("I feel stressed")

        // Wait for AI response
        testScheduler.advanceUntilIdle()

        // Assert final state
        val finalState = viewModel.uiState.value
        assertThat(finalState.messages.last().text).isEqualTo("Here is your CBT technique.")
        assertThat(finalState.messages.last().isUser).isFalse()
        assertThat(finalState.isTyping).isFalse()
    }
    
    @Test
    fun `sendMessage handles API exception by showing error message`() = runTest {
        coEvery { chatSession.sendMessage(any<String>()) } throws Exception("API Key Missing")
        
        testScheduler.advanceUntilIdle()
        
        viewModel.sendMessage("Help")
        
        testScheduler.advanceUntilIdle()
        
        val errorState = viewModel.uiState.value
        assertThat(errorState.messages.last().text).contains("Failed to send message")
        assertThat(errorState.messages.last().isError).isTrue()
        assertThat(errorState.isTyping).isFalse()
    }
}