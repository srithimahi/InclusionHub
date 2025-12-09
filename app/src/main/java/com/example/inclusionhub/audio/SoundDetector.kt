package com.example.inclusionhub.audio

import android.os.Handler
import android.os.Looper
import kotlin.random.Random

class SoundDetector {

    private val handler = Handler(Looper.getMainLooper())
    private var isListening = false

    private val allSounds = listOf(
        "Fire Alarm",
        "Baby Crying",
        "Doorbell",
        "Noise/yelling"
    )

    private var enabledSounds: List<String> = allSounds

    // how often we "check" the environment
    private val detectionIntervalMs = 2000L
    // probability we actually trigger a detection on each check
    private val detectionChancePerCheck = 0.35f

    fun updateEnabledSounds(sounds: List<String>) {
        enabledSounds = if (sounds.isEmpty()) emptyList() else sounds
    }

    fun startListening(onDetect: (String) -> Unit) {
        if (isListening) return
        isListening = true
        simulateDetection(onDetect)
    }

    fun stopListening() {
        isListening = false
        handler.removeCallbacksAndMessages(null)
    }

    private fun simulateDetection(onDetect: (String) -> Unit) {
        if (!isListening) return

        if (enabledSounds.isNotEmpty() && Random.nextFloat() < detectionChancePerCheck) {
            val randomSound = enabledSounds.random()
            onDetect(randomSound)
        }

        handler.postDelayed(
            { simulateDetection(onDetect) },
            detectionIntervalMs
        )
    }
}
