package com.unsmoke.app.core.domain.engine

object QuitCoachData {
    val lessons = listOf(
        "Day 0: The First Step. You have made a massive commitment to your health. Throw away all hidden cigarettes and lighters today.",
        "Day 1: Carbon Monoxide Drop. Your blood oxygen levels are already returning to normal. If a craving hits, remember the 4 D's.",
        "Day 2: The Peak. Nicotine is fully leaving your system today. This is the hardest physical day. Use your NRT if needed!",
        "Day 3: The Dopamine Drop. Your brain is wondering where its free dopamine went. Stay distracted and drink ice-cold water.",
        "Day 4: Senses Returning. Notice anything different? Your sense of smell and taste are beginning to repair themselves.",
        "Day 5: Habit Loops. You aren't just fighting physical withdrawal, you're fighting routines. Change your morning coffee routine to trick your brain.",
        "Day 6: Lung Repair. The cilia (tiny hairs) in your lungs are regrowing and sweeping out debris. You might cough more—this is a good sign!",
        "Day 7: ONE WEEK! You have survived the absolute hardest part of quitting. Reward yourself with something you bought using your saved money.",
        "Day 8: Psychological Cravings. The physical nicotine is gone. Every craving from now on is purely a psychological ghost.",
        "Day 9: Blood Circulation. Your blood circulation is improving rapidly. Walking and exercising will start feeling noticeably easier.",
        "Day 10: Trigger Mapping. Start paying close attention to exactly what triggers your cravings. Is it stress? Boredom? Anger?",
        "Day 11: The 'Just One' Lie. Your brain will try to convince you that 'just one puff' won't hurt. It will. Do not negotiate with your addiction.",
        "Day 12: Gum and Teeth. Your oral health is stabilizing. The risk of gum disease is already beginning to drop.",
        "Day 13: Sleep Normalizing. If you had insomnia during the first week, your sleep patterns should be returning to normal now.",
        "Day 14: TWO WEEKS! You are crushing it. Take a moment to look at your Health Recovery timeline and see how far you've come.",
        "Day 15: Energy Levels. Without carbon monoxide stealing your oxygen, you should be feeling a natural boost in daily energy.",
        "Day 16: The 'Extinction Burst'. Sometimes cravings randomly spike right before they disappear forever. Hold the line.",
        "Day 17: NRT Tapering. If you're using NRT, you might be stepping down your dosage soon. Trust the clinical process.",
        "Day 18: Heart Health. Your risk of a sudden heart attack has already dropped noticeably since the day you quit.",
        "Day 19: Identity Shift. Start calling yourself a 'non-smoker' rather than 'someone trying to quit'. The psychological shift is powerful.",
        "Day 20: Lung Capacity. Your lung function can improve by up to 30% in the first few weeks. Take a deep breath and feel the difference.",
        "Day 21: Three Weeks! It takes 21 days to break a habit. You have successfully dismantled your smoking routine.",
        "Day 22: Stress Management. Remember that smoking never actually cured stress, it only cured the stress of nicotine withdrawal.",
        "Day 23: Coughing & Mucus. Your lungs are likely fully cleared of the initial debris now. Breathing is easier and less congested.",
        "Day 24: Financial Freedom. Look at your 'Money Saved' tracker. What are you going to do with your newfound wealth?",
        "Day 25: Olfactory Healing. Go smell some flowers or your favorite food. The repair to your olfactory nerve is largely complete.",
        "Day 26: Immune System. Your immune system is stronger. You are now significantly less likely to catch severe colds and respiratory infections.",
        "Day 27: Skin Elasticity. The blood flow to your skin is restored, meaning more oxygen and nutrients. Your skin is literally glowing more.",
        "Day 28: FOUR WEEKS! A massive milestone. Most people who make it to 28 days are 5x more likely to quit for good.",
        "Day 29: The Marathon. The initial sprint is over. Now it's about maintaining vigilance against the rare, but sneaky, phantom cravings.",
        "Day 30+: ONE MONTH! You are a non-smoker. The physical addiction is dead. The psychological habit is broken. Welcome to your new life."
    )

    fun getLessonForDay(daysFree: Int): String {
        return if (daysFree < 0) {
            lessons[0]
        } else if (daysFree >= lessons.size - 1) {
            lessons.last()
        } else {
            lessons[daysFree]
        }
    }
}
