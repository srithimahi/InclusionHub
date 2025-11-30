package com.example.inclusionhub.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import java.util.Locale

class SpeechToTextManager(private val context: Context) {
    private val speechRecognizer: SpeechRecognizer =
        SpeechRecognizer.createSpeechRecognizer(context)

    private var resultCallback: ((String) -> Unit)? = null
    private var partialCallback: ((String) -> Unit)? = null

    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
    }

    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("STT", "Ready for speech")
            }

            override fun onBeginningOfSpeech() {
                Log.d("STT", "Beginning of Speech")
            }
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                Log.d("STT", "End of Speech")
            }
            override fun onError(error: Int) {
                Log.d("STT", "Error: $error")
            }

            override fun onResults(results: Bundle?) {
                val matches =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = matches?.firstOrNull()
                Log.d("STT", "Results: $text")
                if(!text.isNullOrEmpty()) {
                    resultCallback?.invoke(text)
                }
            }
            override fun onPartialResults(partialResults: Bundle?) {
                val matches =
                    partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = matches?.firstOrNull()
                Log.d("STT", "Partial Results: $text")
                if(!text.isNullOrEmpty()) {
                    partialCallback?.invoke(text)
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startListening(
        onPartial: (String) -> Unit,
        onFinal: (String) -> Unit
    ) {
        partialCallback = onPartial
        resultCallback = onFinal
        speechRecognizer.startListening(recognizerIntent)
    }
    fun stopListening() {
        speechRecognizer.stopListening()
    }
    fun destroy() {
        speechRecognizer.destroy()
    }
}
