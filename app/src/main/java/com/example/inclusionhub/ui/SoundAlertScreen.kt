package com.example.inclusionhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton



data class SoundAlert(
    val name: String,
    val description: String,
    var enabled: Boolean = false
)
@Composable
fun SoundAlertScreen(onBack: () -> Unit) {

    var detectedSound by remember { mutableStateOf("") }

    val alertsList = remember {
        mutableStateListOf(
            SoundAlert("Fire Alarm", "detects fire alarms or high pitched beeps"),
            SoundAlert("Baby Crying", "to be written"),
            SoundAlert("Doorbell", "to be written"),
            SoundAlert("Noise/yelling", "to be written")
        )
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

            if(detectedSound.isNotEmpty()){
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3C4)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column (
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
                            alert.enabled = toggled
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AlertCard(
    title: String,
    description: String,
    onToggle: (Boolean) -> Unit,
    enabled: Boolean
){
    var checked by rememberSaveable { mutableStateOf(enabled) }

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
             Column(modifier = Modifier.weight(1f)){
                 Text(title, style = MaterialTheme.typography.titleMedium)
                 Spacer(Modifier.height(4.dp))
                 Text(
                     description,
                     style = MaterialTheme.typography.bodyMedium,
                     color = Color.Gray
                 )
             }
             Switch(
                 checked = checked,
                 onCheckedChange = { isChecked ->
                     checked = isChecked
                     onToggle(isChecked)
                 }
             )
         }
     }
}

