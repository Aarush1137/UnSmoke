package com.unsmoke.app.core.domain.engine

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.Duration

class CalculationEngineTest {

    private val oneDayMs = 24L * 60 * 60 * 1000

    @Test
    fun smokeFreeDuration_calculatesCorrectly() {
        val start = 100000L
        val end = start + oneDayMs
        val duration = CalculationEngine.smokeFreeDuration(start, end)
        assertThat(duration.toDays()).isEqualTo(1)
        assertThat(duration.toHours()).isEqualTo(24)
    }

    @Test
    fun smokeFreeDuration_clampsToZeroIfInFuture() {
        val start = 200000L
        val end = 100000L
        val duration = CalculationEngine.smokeFreeDuration(start, end)
        assertThat(duration.toMillis()).isEqualTo(0)
    }

    @Test
    fun cigarettesAvoided_calculatesCorrectly() {
        val start = 0L
        val end = 3L * oneDayMs // 3 days
        val cigsPerDay = 10.0
        val avoided = CalculationEngine.cigarettesAvoided(start, cigsPerDay, end)
        assertThat(avoided).isWithin(0.01).of(30.0)
    }

    @Test
    fun cigarettesAvoided_handlesFractions() {
        val start = 0L
        val end = (2.5 * oneDayMs).toLong() // 2.5 days
        val cigsPerDay = 20.0
        val avoided = CalculationEngine.cigarettesAvoided(start, cigsPerDay, end)
        assertThat(avoided).isWithin(0.01).of(50.0)
    }

    @Test
    fun grossMoneySaved_calculatesCorrectly() {
        val avoided = 600.0
        val pricePerCig = 0.50
        val saved = CalculationEngine.grossMoneySaved(avoided, pricePerCig)
        assertThat(saved).isWithin(0.01).of(300.0)
    }

    @Test
    fun netMoneySaved_deductsNrtExpenditure() {
        val gross = 300.0
        val nrt = 50.0
        val net = CalculationEngine.netMoneySaved(gross, nrt)
        assertThat(net).isWithin(0.01).of(250.0)
    }

    @Test
    fun netMoneySaved_clampsToZeroIfNrtExceedsGross() {
        val gross = 300.0
        val nrt = 500.0
        val net = CalculationEngine.netMoneySaved(gross, nrt)
        assertThat(net).isWithin(0.01).of(0.0)
    }

    @Test
    fun nrtExpenditure_sumsCorrectly() {
        val usages = listOf(
            Pair(2, 5.0),  // 2 items @ 5.0 = 10.0
            Pair(1, 15.0)  // 1 item @ 15.0 = 15.0
        )
        val total = CalculationEngine.nrtExpenditure(usages)
        assertThat(total).isWithin(0.01).of(25.0)
    }

    @Test
    fun packsAvoided_calculatesCorrectly() {
        val avoided = 150.0
        val packs = CalculationEngine.packsAvoided(avoided, 20)
        assertThat(packs).isWithin(0.01).of(7.5)
    }
}