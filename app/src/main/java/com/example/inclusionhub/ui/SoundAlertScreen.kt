@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.inclusionhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import com.example.inclusionhub.audio.SoundDetector
import com.example.inclusionhub.alerts.VibrationController

data class SoundAlert(
    val name: String,
    val description: String,
    val enabled: Boolean = false
)

@Composable
fun SoundAlertScreen(onBack: () -> Unit) {

    val context = LocalContext.current
    val vibrationController = remember { VibrationController(context) }
    val soundDetector = remember { SoundDetector() }

    var detectedSound by remember { mutableStateOf("") }
    var isListening by remember { mutableStateOf(false) }

    val alertsList = remember {
        mutableStateListOf(
            SoundAlert("Fire Alarm", "Detects fire alarms or high pitched beeps", enabled = true),
            SoundAlert("Baby Crying", "Detects baby crying sounds", enabled = true),
            SoundAlert("Doorbell", "Detects doorbell chimes", enabled = true),
            SoundAlert("Noise/yelling", "Detects loud yelling or shouting", enabled = true)
        )
    }

    // Start/stop listening when isListening changes
    LaunchedEffect(isListening) {
        if (isListening) {
            soundDetector.updateEnabledSounds(
                alertsList.filter { it.enabled }.map { it.name }
            )

            soundDetector.startListening { soundName ->
                detectedSound = soundName
                vibrationController.vibrateForSound(soundName)
            }
        } else {
            soundDetector.stopListening()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sound Alerts") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your phone will signal alerts when important sound is detected.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (detectedSound.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3C4)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Sound Detected:", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(detectedSound, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(alertsList) { alert ->
                    AlertCard(
                        title = alert.name,
                        description = alert.description,
                        enabled = alert.enabled,
                        onToggle = { toggled ->
                            val index = alertsList.indexOf(alert)
                            if (index != -1) {
                                alertsList[index] = alertsList[index].copy(enabled = toggled)
                            }
                            if (isListening) {
                                soundDetector.updateEnabledSounds(
                                    alertsList.filter { it.enabled }.map { it.name }
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { isListening = true },
                    enabled = !isListening,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Start Listening")
                }
                OutlinedButton(
                    onClick = { isListening = false },
                    enabled = isListening,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Stop Listening")
                }
            }
        }
    }
}

@Composable
fun AlertCard(
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(
                    description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Switch(
                checked = enabled,
                onCheckedChange = { isChecked ->
                    onToggle(isChecked)
                }
            )
        }
    }
}

