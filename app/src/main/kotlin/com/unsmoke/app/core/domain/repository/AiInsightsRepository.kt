package com.unsmoke.app.core.domain.repository

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.Chat
import com.unsmoke.app.core.data.database.entity.CravingEventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiInsightsRepository @Inject constructor() {

    // Fetched securely from local.properties via BuildConfig
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = com.unsmoke.app.BuildConfig.GEMINI_API_KEY
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
                append("- Intensity: ${craving.intensity}/10, Trigger: ${craving.trigger}, ")
                append("Time: ${craving.timestamp}\n")
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

    fun startCbtChatSession(
        userName: String,
        cravings: List<CravingEventEntity>,
        daysSmokeFree: Int,
        usesNRT: Boolean
    ): Chat {
        val systemInstructionText = buildString {
            append("You are 'UnSmoke Coach', an empathetic, expert addiction recovery AI therapist.\n")
            append("The user's name is $userName. They have been smoke-free for $daysSmokeFree days.\n")
            if (usesNRT) append("They are currently using Nicotine Replacement Therapy or vaping to step down.\n")
            if (cravings.isNotEmpty()) {
                val triggers = cravings.mapNotNull { it.trigger }.flatMap { it.split(",") }.filter { it.isNotBlank() }
                val topTrigger = triggers.groupingBy { it.trim() }.eachCount().maxByOrNull { it.value }?.key
                append("Their top craving trigger historically is: $topTrigger.\n")
            }
            append("Keep responses extremely concise (1-3 sentences max) and conversational. Focus on CBT (Cognitive Behavioral Therapy) grounding techniques and urge surfing. Never break character.")
        }

        val sessionModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = com.unsmoke.app.BuildConfig.GEMINI_API_KEY,
            systemInstruction = content { text(systemInstructionText) }
        )

        return sessionModel.startChat()
    }
    fun generateComprehensiveInsight(
        cravings: List<CravingEventEntity>,
        nrtUsages: List<com.unsmoke.app.core.data.database.entity.NRTUsageEntity>,
        daysSmokeFree: Int
    ): Flow<String> = flow {
        if (cravings.isEmpty() && nrtUsages.isEmpty()) {
            emit("Log some cravings or NRT usage so I can analyze your progress!")
            return@flow
        }

        val prompt = buildString {
            append("You are an expert addiction recovery AI coach analyzing a user's quit-smoking data.\n")
            append("The user has been smoke-free for $daysSmokeFree days.\n\n")
            append("Here is their recent craving log (Intensity out of 10, Trigger, Outcome):\n")
            cravings.takeLast(20).forEach { craving ->
                append("- Intensity: ${craving.intensity}/10, Trigger: ${craving.trigger}, Outcome: ${craving.outcome}\n")
            }
            append("\nHere is their recent Nicotine Replacement Therapy (NRT) usage log:\n")
            nrtUsages.takeLast(20).forEach { nrt ->
                append("- Product ID: ${nrt.productId}, Quantity: ${nrt.quantity}\n")
            }
            append("\nBased on this data, please provide:\n")
            append("1. A rating on how their quit journey is going (e.g. Excellent, Struggling, On Track).\n")
            append("2. A personalized suggestion or CBT coping mechanism specifically targeting their triggers or NRT patterns.\n")
            append("Keep it concise, empathetic, and under 4 sentences.")
        }

        try {
            val response = generativeModel.generateContent(
                content { text(prompt) }
            )
            emit(response.text ?: "I couldn't analyze your data right now. Stay strong!")
        } catch (e: Exception) {
            emit("Analysis temporarily unavailable. Keep pushing forward!")
        }
    }
}