package com.unsmoke.app.core.domain.engine

object QuoteEngine {
    enum class QuoteCategory {
        EARLY_DAYS, FIRST_WEEK, CRAVING_ACTIVE, CRAVING_DEFEATED, AFTER_LAPSE, MILESTONE, EVENING, MORNING, GENERAL
    }

    private val quotes = mapOf(
        QuoteCategory.CRAVING_ACTIVE to listOf(
            "Craving isn't a command.",
            "You didn't need to make the craving disappear. You only needed to outlast it.",
            "An urge is temporary.",
            "Don't negotiate with a 10-minute craving.",
            "Your job right now isn't to eliminate the craving. It's to let it pass.",
            "20 minutes ago you wanted a cigarette. You didn't smoke one."
        ),
        QuoteCategory.CRAVING_DEFEATED to listOf(
            "One craving survived. So did you.",
            "You chose yourself today.",
            "You are collecting proof that you can do this.",
            "You wanted it. You waited. You won."
        ),
        QuoteCategory.EARLY_DAYS to listOf(
            "Three days is no longer an accident. It's a decision you're repeating.",
            "Today doesn't need to be easy. It just needs to be smoke-free.",
            "Your old routine is losing its grip."
        ),
        QuoteCategory.AFTER_LAPSE to listOf(
            "Study the moment, not the mistake.",
            "The cigarette isn't solving stress. It's training your brain."
        )
    )

    fun getQuote(category: QuoteCategory): String {
        return quotes[category]?.random() ?: "You can do this."
    }
}
