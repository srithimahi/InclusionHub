package com.example.inclusionhub.stt
import android.content.Context

class SpeechToTextManager(context: Context) {
    fun startListening(onResult: (String) -> Unit) { ... }
    fun stopListening() { ... }
}
