package com.unsmoke.app.core.domain.engine

object NRTTaperingEngine {
    
    data class TaperingRecommendation(
        val weekNumber: Int,
        val dosage: String,
        val frequency: String,
        val maxPiecesPerDay: Int,
        val instructions: String
    )

    fun getNicotexGumPlan(weeksSmokeFree: Int, initialDosageMg: Int): TaperingRecommendation {
        return when (weeksSmokeFree) {
            in 0..6 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 1-2 hours",
                maxPiecesPerDay = 24,
                instructions = "Chew slowly until peppery, then park between cheek and gum."
            )
            in 7..9 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 2-4 hours",
                maxPiecesPerDay = 14,
                instructions = "Start reducing frequency. Wait longer between pieces."
            )
            in 10..12 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 piece every 4-8 hours",
                maxPiecesPerDay = 6,
                instructions = "You are almost there! Only use when absolutely necessary."
            )
            else -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "0mg",
                frequency = "None",
                maxPiecesPerDay = 0,
                instructions = "You have completed the 12-week NRT tapering program!"
            )
        }
    }

    fun getPatchPlan(weeksSmokeFree: Int, initialDosageMg: Int): TaperingRecommendation {
        return when (weeksSmokeFree) {
            in 0..6 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "Step 1 (21mg or ${initialDosageMg}mg)",
                frequency = "1 patch per day",
                maxPiecesPerDay = 1,
                instructions = "Apply to clean, dry, hairless skin on upper body. Rotate sites daily."
            )
            in 7..9 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "Step 2 (14mg)",
                frequency = "1 patch per day",
                maxPiecesPerDay = 1,
                instructions = "Tapering down. Keep rotating application sites."
            )
            in 10..12 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "Step 3 (7mg)",
                frequency = "1 patch per day",
                maxPiecesPerDay = 1,
                instructions = "Final step! Only 7mg a day to help you adjust to zero nicotine."
            )
            else -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "0mg",
                frequency = "None",
                maxPiecesPerDay = 0,
                instructions = "You have completed the 12-week NRT tapering program!"
            )
        }
    }

    fun getNicotexLozengePlan(weeksSmokeFree: Int, initialDosageMg: Int): TaperingRecommendation {
        return when (weeksSmokeFree) {
            in 0..6 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 lozenge every 1-2 hours",
                maxPiecesPerDay = 20,
                instructions = "Let it dissolve slowly in your mouth. Do NOT chew or swallow whole. Do not eat/drink 15m before."
            )
            in 7..9 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 lozenge every 2-4 hours",
                maxPiecesPerDay = 14,
                instructions = "Start reducing frequency. Wait longer between lozenges."
            )
            in 10..12 -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "${initialDosageMg}mg",
                frequency = "1 lozenge every 4-8 hours",
                maxPiecesPerDay = 6,
                instructions = "You are almost there! Only use when absolutely necessary."
            )
            else -> TaperingRecommendation(
                weekNumber = weeksSmokeFree + 1,
                dosage = "0mg",
                frequency = "None",
                maxPiecesPerDay = 0,
                instructions = "You have completed the 12-week NRT tapering program!"
            )
        }
    }
}