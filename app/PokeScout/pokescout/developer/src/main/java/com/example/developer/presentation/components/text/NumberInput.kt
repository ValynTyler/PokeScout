package com.example.developer.presentation.components.text

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NumberInput(
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
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Preview
@Composable
fun NumberInputPreview() {
    NumberInput("Enter a number...", "987")
}