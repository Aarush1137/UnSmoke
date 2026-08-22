package com.unsmoke.app.core.domain.engine

object NRTTaperingEngine {
    
    data class TaperingRecommendation(
        val weekNumber: Int,
        val dosage: String,
        val frequency: String,
        val instructions: String
    )

    fun getNicotexGumPlan(weeksSmokeFree: Int, initialDosageMg: Int): TaperingRecommendation {
        return when (weeksSmokeFree) {
            in 0..6 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 1-2 hours",
                instructions = "Chew slowly until you feel a peppery taste, then park it between your cheek and gum."
            )
            in 7..9 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 2-4 hours",
                instructions = "Start reducing frequency. Wait longer between pieces."
            )
            in 10..12 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 4-8 hours",
                instructions = "You are almost there! Only use when absolutely necessary."
            )
            else -> TaperingRecommendation(
                weekNumber = weeksSmokeFree,
                dosage = "0mg",
                frequency = "None",
                instructions = "You have completed the 12-week NRT tapering program!"
            )
        }
    }
}


