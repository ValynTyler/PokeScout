package com.example.leader.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PokeballButton(
    outerRingColor: Color,
    innerCircleColor: Color,
    alignment: Alignment,
    yOffset: Dp,
    clickable: Boolean = false,
    onGloballyPositioned: (LayoutCoordinates) -> Unit = {},
    onClick: () -> Unit = {},
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(alignment)
                .offset(y = yOffset)
                .size(120.dp)
                .clip(CircleShape)
                .background(color = outerRingColor)
                .onGloballyPositioned {  }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(color = innerCircleColor)
                    .clickable(enabled = clickable) { onClick() }
            )
        }
    }
}