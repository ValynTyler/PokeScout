package com.example.pokescoutdeveloper.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextInput(
    labelText: String,
    value: String = "",
    onChange: (String) -> Unit = {},
) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(labelText) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun TextInputPreview() {
    TextInput("Hello, Dave!", "Hi, Jimmy!")
}