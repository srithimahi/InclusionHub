package com.example.inclusionhub.ui.components
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier

@Composable
fun BigButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}
