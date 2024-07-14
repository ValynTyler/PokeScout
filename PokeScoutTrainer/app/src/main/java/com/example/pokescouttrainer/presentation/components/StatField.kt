package com.example.pokescouttrainer.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatField(field: String, entry: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("$field:")
        Text(
            entry,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun StatFieldPreview() {

}