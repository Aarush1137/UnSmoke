package com.unsmoke.app.core.device

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HapticGroundingManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun playInhalePulse() {
        if (vibrator?.hasVibrator() == true) {
            val effect = VibrationEffect.createWaveform(
                longArrayOf(0, 50, 100, 50, 100, 50),
                intArrayOf(0, 50, 0, 100, 0, 150),
                -1
            )
            vibrator.vibrate(effect)
        }
    }

    fun playExhalePulse() {
        if (vibrator?.hasVibrator() == true) {
            val effect = VibrationEffect.createWaveform(
                longArrayOf(0, 150, 100, 100, 100, 50),
                intArrayOf(0, 150, 0, 100, 0, 50),
                -1
            )
            vibrator.vibrate(effect)
        }
    }

    fun playHoldPulse() {
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
            } else {
                vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        }
    }

    fun cancel() {
        vibrator?.cancel()
    }
}