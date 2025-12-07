package com.example.inclusionhub.tts
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale


class TextToSpeechManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech = TextToSpeech(context, this)
    private var isready = false

    override fun onInit(status: Int){
        isready = status == TextToSpeech.SUCCESS
        if(isready){
            tts.language = Locale.getDefault()
            tts.setSpeechRate(1.0f)
            Log.e("TTS", "TTS Ready")
        } else {
            Log.e("TTS", "TTS Initialization Failed")
        }
    }

    fun speak(text: String){
        if(!isready || text.isBlank()) return
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_id")
    }

    fun shutdown(){
        tts.stop()
        tts.shutdown()
    }


}
