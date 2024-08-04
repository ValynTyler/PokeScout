package com.example.pokemon.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.pokemon.presentation.theme.PokeBallColors

@Composable
fun PokeballScaffold(
    isClosed: Boolean,
    pokeBallColors: PokeBallColors,
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
        )
        // Content
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(pokeBallColors.dividerColor)
            .onGloballyPositioned { coordinates ->
                yDelta = with(localDensity) { coordinates.size.height.toDp() }
            }
        ) {
            Box {
                content()
            }
        }
        // Bottom pokeball half
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomHalfHeight)
                .background(pokeBallColors.bottomHalfColor)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(pokeBallColors.dividerColor)
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
                .background(pokeBallColors.topHalfColor)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(pokeBallColors.dividerColor)
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
            circleColor = pokeBallColors.emptyColor,
            ringColor = pokeBallColors.dividerColor,
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
                pokeBallColors.bottomHalfColor,
                pokeBallColors.dividerColor,
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