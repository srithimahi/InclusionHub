package com.example.inclusionhub.alerts
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build

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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(400)
                }
            }

            "pattern" -> {
                val pattern = longArrayOf(0,150,100,150)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createWaveform(pattern, -1))
                } else {
                    @Suppress("DEPRECATION")
                    v.vibrate(pattern, -1)
                }
            }
            else -> {
            }
        }
    }
}
