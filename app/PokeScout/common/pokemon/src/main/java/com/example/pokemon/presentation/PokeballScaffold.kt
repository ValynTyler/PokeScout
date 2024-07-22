package com.example.pokemon.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun PokeballScaffold(
    isClosed: Boolean,
    tophalfColor: Color,
    bottomHalfColor: Color,
    backgroundColor: Color,
    uiColor: Color,
    buttonSize: Dp = 120.dp,
    topHalfHeight: Dp = 130.dp,
    bottomHalfHeight: Dp = 100.dp,
    onClicked: () -> Unit = {},
    pokeballDecoration: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    var yDelta by remember { mutableStateOf(0.dp) }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
    ) {
        val localDensity = LocalDensity.current
        // Top pokeball half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topHalfHeight)
                .border(5.dp, uiColor)
        )
        // Content
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(backgroundColor)
            .onGloballyPositioned { coordinates ->
                yDelta = with(localDensity) { coordinates.size.height.toDp() }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 0.dp)
                    .background(uiColor)
            ) {
                content()
            }
        }
        // Bottom pokeball half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomHalfHeight)
                .background(bottomHalfColor)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(backgroundColor)
            )
        }
    }

    // Cover
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.dp)
                .weight(1f)
                .background(tophalfColor)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(backgroundColor)
            )
        }
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .animateContentSize()
                .height(if (!isClosed) yDelta else 0.dp)
        )
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .height(bottomHalfHeight)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PokeballButton(
            circleColor = uiColor,
            ringColor = backgroundColor,
            buttonSize = buttonSize,
            modifier = Modifier
                .offset(y = buttonSize / 2 - bottomHalfHeight)
                .align(Alignment.BottomCenter)
                .zIndex(1f)
        ) { onClicked() }
    }

    Column {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .animateContentSize()
                .height(if (isClosed) yDelta else 0.dp)
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            PokeballButton(
                bottomHalfColor,
                backgroundColor,
                buttonSize,
                Modifier
                    .align(Alignment.TopCenter)
                    .zIndex(2f)
                    .offset(y = topHalfHeight - buttonSize / 2)
            ) { onClicked() }
        }
    }

    Column {
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxWidth()
                .animateContentSize()
                .height(if (isClosed) yDelta else 0.dp)
        )
        pokeballDecoration()
    }
}