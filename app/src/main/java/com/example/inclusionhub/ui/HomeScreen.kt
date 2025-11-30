package com.example.inclusionhub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onConversationClick: () -> Unit,
    onSoundAlertClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "InclusionHub",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onConversationClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text("Conversation Mode", style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSoundAlertClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Text("Sound Alert Mode", style = MaterialTheme.typography.headlineSmall)
        }
    }
}