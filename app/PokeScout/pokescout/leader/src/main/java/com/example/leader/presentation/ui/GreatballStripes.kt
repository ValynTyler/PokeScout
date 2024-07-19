package com.example.leader.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeballRed

@Preview
@Composable
fun GreatballStripes(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = (-125).dp, x = (-25).dp)
                .height(400.dp)
                .width(75.dp)
                .rotate(-45f)
                .background(color = PokeballRed)
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = (-125).dp, x = 25.dp)
                .height(400.dp)
                .width(75.dp)
                .rotate(45f)
                .background(color = PokeballRed)
        )
    }
}