package com.example.inclusionhub.audio

import android.os.Handler
import android.os.Looper

class SoundDetector {
    private val handler = Handler(Looper.getMainLooper())
    private var  isListening = false

    private val fakeSounds = listOf(
        "Door Knock",
        "Beeping",
        "Dog Bark",
        "Alarm Ring",
        "Clapping"
    )

    fun startListening(onDetect: (String) -> Unit) {
        if(isListening) return
        isListening = true
        simulateDetection(onDetect)
    }
    fun stopListening() {
        isListening = false
        handler.removeCallbacksAndMessages(null)
    }

    private fun simulateDetection(onDetect: (String) -> Unit) {
        if(!isListening)return
        val randomSound = fakeSounds.random()
        onDetect(randomSound)
        handler.postDelayed({
            simulateDetection(onDetect) },
            3000)
    }
}
