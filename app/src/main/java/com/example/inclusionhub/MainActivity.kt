package com.example.inclusionhub
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat;
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.inclusionhub.stt.SpeechToTextManager
import com.example.inclusionhub.tts.TextToSpeechManager
import com.example.inclusionhub.ui.theme.InclusionHubTheme
import com.example.inclusionhub.ui.HomeScreen
import com.example.inclusionhub.ui.ConversationScreen
import com.example.inclusionhub.ui.SoundAlertScreen

private const val RECORD_AUDIO_REQUEST_CODE = 1001

class MainActivity : ComponentActivity() {
    private lateinit var ttsManager: TextToSpeechManager
    private lateinit var sttManager: SpeechToTextManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ensureAudioPermission()

        ttsManager = TextToSpeechManager(this)
        sttManager = SpeechToTextManager(this)

        setContent {
            InclusionHubTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InclusionHubApp(ttsManager, sttManager)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsManager.shutdown()
        sttManager.stopListening()
    }
    private fun ensureAudioPermission() {
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        if (!granted) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                RECORD_AUDIO_REQUEST_CODE
            )
        }
    }

    @Composable

    fun InclusionHubApp(ttsManager: TextToSpeechManager, sttManager: SpeechToTextManager) {
        var currentScreen by remember { mutableStateOf("home") }

        when (currentScreen) {
            "home" -> HomeScreen(
                onConversationClick = { currentScreen = "conversation" },
                onSoundAlertClick = { currentScreen = "soundalert" }
            )

            "conversation" -> ConversationScreen(
                onBack = { currentScreen = "home" },
                ttsManager = ttsManager,
                sttManager = sttManager
            )

            "soundalert" -> SoundAlertScreen(
                onBack = { currentScreen = "home" }
            )
        }
    }
}
