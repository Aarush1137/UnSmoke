package com.unsmoke.app.core.domain.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiInsightsRepository @Inject constructor() {

    // Note: In a production app, the API key must be securely fetched from a backend or build config.
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_API_KEY_HERE" // We will configure this securely later
    )

    fun generateRelapsePrediction(cravings: List<CravingEventEntity>): Flow<String> = flow {
        if (cravings.isEmpty()) {
            emit("Not enough data to analyze triggers yet. Keep logging your cravings!")
            return@flow
        }

        val prompt = buildString {
            append("You are an expert addiction recovery AI coach. ")
            append("Analyze the following recent cravings from a user trying to quit smoking. ")
            append("Identify their primary triggers, high-risk times of day, and provide a short, encouraging 2-sentence actionable advice.\n\n")
            
            cravings.takeLast(20).forEach { craving ->
                append("- Intensity: /10, Trigger: , ")
                append("Time: \n")
            }
        }

        try {
            val response = generativeModel.generateContent(
                content { text(prompt) }
            )
            emit(response.text ?: "I'm here to support you, but I couldn't analyze your data right now.")
        } catch (e: Exception) {
            emit("Analysis temporarily unavailable. Stay strong!")
        }
    }
}