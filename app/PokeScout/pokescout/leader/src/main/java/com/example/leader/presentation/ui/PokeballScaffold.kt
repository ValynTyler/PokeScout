package com.example.leader.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.viewmodel.LeaderState

@Composable
fun PokeballScaffold(
    modifier: Modifier = Modifier,
    state: LeaderState = LeaderState(),
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    var yDelta by remember { mutableStateOf(0.dp) }
    Column(
        modifier = Modifier
            .height(800.dp)
    ) {
        val localDensity = LocalDensity.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .background(color = GreatballBlue)
                .height(100.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    yDelta = with(localDensity) { coordinates.size.height.toDp() }
                }
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = PokeballWhite)
        )
    }

    // Stripes
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .align(Alignment.TopStart)
            .offset(y = (-125).dp)
            .height(200.dp)
            .width(75.dp)
            .rotate(-30f)
            .background(color = PokeballRed)
        )
        Box(modifier = Modifier
            .align(Alignment.TopEnd)
            .offset(y = (-125).dp)
            .height(200.dp)
            .width(75.dp)
            .rotate(30f)
            .background(color = PokeballRed)
        )
    }
    PokeballButton(
        PokeballGrey,
        ThemeDarkGrey,
        Alignment.BottomCenter,
        (-60).dp,
        clickable = true,
        onClick = onClick,
    )
    PokeballButton(
        PokeballGrey,
        PokeballWhite,
        Alignment.TopCenter,
//        60.dp + if (state.isWritingNfc) yDelta else 0.dp,
        60.dp,
        clickable = true,
        onClick = onClick,
    )
}

@Preview
@Composable
fun PokeballScaffoldPreview() {
    PokeScoutTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PokeballScaffold(
                state = LeaderState(
                    isWritingNfc = true
                )
            ) {

            }
        }
    }
}