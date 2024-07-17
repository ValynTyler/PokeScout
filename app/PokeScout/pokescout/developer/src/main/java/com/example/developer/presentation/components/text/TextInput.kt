package com.example.developer.presentation.components.text

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextInput(
    labelText: String,
    value: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onChange: (String) -> Unit = {},
) {
    TextField(
        value = value,
        onValueChange = onChange,
        label = { Text(labelText) },
        enabled = enabled,
        modifier = modifier
    )
}

@Preview
@Composable
fun TextInputPreview() {
    TextInput("Hello, Dave!", "Hi, Jimmy!")
}