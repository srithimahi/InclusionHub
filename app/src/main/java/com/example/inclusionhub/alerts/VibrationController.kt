package com.example.inclusionhub.alerts

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class VibrationController(private val context: Context) {

    private val vibrator: Vibrator? =
        context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator

    fun vibratePattern(type: String) {
        val v = vibrator ?: return

        when (type) {
            "short" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(150)
                }
            }

            "long" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(400)
                }
            }

            "pattern" -> {
                val pattern = longArrayOf(0, 150, 100, 150)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }
        }
    }

    // 🔔 NEW: one function that maps each sound → unique pattern
    fun vibrateForSound(soundName: String) {
        val v = vibrator ?: return

        when (soundName) {
            // Fire alarm = long repeating pulses
            "Fire Alarm" -> {
                val pattern = longArrayOf(0, 400, 150, 400, 150, 400)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }

            // Baby crying = soft, uneven pattern
            "Baby Crying" -> {
                val pattern = longArrayOf(0, 200, 100, 350, 150, 250)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }

            // Yelling = rapid short bursts
            "Noise/yelling" -> {
                val pattern = longArrayOf(0, 120, 60, 120, 60, 120, 60, 120)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }

            // Doorbell = single medium pulse
            "Doorbell" -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(250)
                }
            }

            else -> {
                // fallback
                vibratePattern("short")
            }
        }
    }
}
