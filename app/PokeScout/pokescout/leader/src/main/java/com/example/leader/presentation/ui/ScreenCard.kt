package com.example.leader.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.ThemeDarkGrey

@Composable
fun ScreenCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .width(300.dp)
            .height(180.dp)
            .background(color = ThemeDarkGrey)
    ) {
        content()
    }
}

@Preview
@Composable
fun OptionCardPreview() {
    ScreenCard()
}