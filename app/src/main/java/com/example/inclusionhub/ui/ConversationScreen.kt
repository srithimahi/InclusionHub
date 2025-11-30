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


@Composable
fun ConversationScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val sttManager = remember { SpeechToTextManager(context) }

    var caption by remember { mutableStateOf("Press Start and speak...") }

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
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(text = caption)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        sttManager.startListening(
                            onPartial = { partialText ->
                                caption = partialText
                            },
                            onFinal = { finalText ->
                                caption = finalText
                            }
                        )
                    }
                ) {
                    Text("Start")
                }

                Button(
                    onClick = {
                        sttManager.stopListening()
                    }
                ) {
                    Text("Stop")
                }
            }
        }
    }
}

