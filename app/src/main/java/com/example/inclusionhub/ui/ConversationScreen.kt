@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.inclusionhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import com.example.inclusionhub.stt.SpeechToTextManager
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.inclusionhub.tts.TextToSpeechManager


@Composable
fun ConversationScreen(
    onBack: () -> Unit,
    ttsManager: TextToSpeechManager,
    sttManager: SpeechToTextManager
) {

    val context = LocalContext.current
    val sttManager = remember { SpeechToTextManager(context) }

    var caption by remember { mutableStateOf("Press Start and speak...") }
    var textToSpeech by remember { mutableStateOf("")}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Conversation Mode") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "Speech to Text",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Box(modifier = Modifier.padding(20.dp)) {
                    Text(text = caption)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        sttManager.startListening(
                            onPartial = { caption = it },
                            onFinal = { caption = it }
                        )
                    }
                ) {
                    Text("Start")
                }

                Button(
                    onClick = { sttManager.stopListening() }
                ) {
                    Text("Stop")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Text to Speech",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = textToSpeech,
                onValueChange = { textToSpeech = it },
                label = { Text("Type your text then click Speak")},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        ttsManager.speak(textToSpeech)
                    }
                ) {
                    Text("Speak")
                }
            }
        }
    }
}


