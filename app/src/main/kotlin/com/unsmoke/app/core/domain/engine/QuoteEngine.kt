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
        ),
        QuoteCategory.FIRST_WEEK to listOf(
            "The physical nicotine is nearly out of your system. You are through the toughest part.",
            "Seven days smoke-free. Your taste and smell are already returning.",
            "Every day this week has made your lungs a little cleaner and your will a little stronger.",
            "You are breaking decades of habit, one smoke-free hour at a time."
        ),
        QuoteCategory.MILESTONE to listOf(
            "Every milestone is a testament to your discipline and resilience.",
            "Look back at how far you've come. The cravings that felt impossible are in your past.",
            "Celebrate this victory â€” you've earned every breath of clean air.",
            "You didn't just quit smoking. You took back control of your health and future."
        ),
        QuoteCategory.MORNING to listOf(
            "Start your morning with a deep breath of fresh air. Your body thanks you.",
            "A brand new smoke-free day ahead. You have the power to protect your streak.",
            "Morning cravings pass quickly. Sip some water, breathe deep, and begin strong.",
            "Waking up smoke-free is the greatest gift you give yourself every morning."
        ),
        QuoteCategory.EVENING to listOf(
            "You made it through another day smoke-free. Rest easy tonight.",
            "Tonight, your lungs are healing as you sleep. Be proud of your choices today.",
            "Another day conquered. Tomorrow will be even easier.",
            "Reflect on the urges you conquered today. You are building unbreakable mental strength."
        ),
        QuoteCategory.GENERAL to listOf(
            "The secret of getting ahead is getting started.",
            "Freedom from smoking isn't deprivation â€” it's liberation.",
            "Your body is capable of amazing healing. Give it the clean air it deserves.",
            "Every time you resist a craving, your brain physically rewires itself toward freedom.",
            "Small daily victories compound into lifelong health."
        )
    )

    fun getQuote(category: QuoteCategory): String {
        return quotes[category]?.random() ?: "You can do this."
    }
}
