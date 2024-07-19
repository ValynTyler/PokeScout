package com.example.leader.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.viewmodel.LeaderState


@Composable
fun PokeballScaffold(
    tophalfColor: Color,
    bottomHalfColor: Color,
    backgroundColor: Color,
    uiColor: Color,
    state: LeaderState,
    onInputEvent: (InputEvent) -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    var yDelta by remember { mutableStateOf(0.dp) }
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()

    ) {
        val localDensity = LocalDensity.current
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(bottomHalfColor)
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(backgroundColor)
            .onGloballyPositioned { coordinates ->
                yDelta = with(localDensity) { coordinates.size.height.toDp() }
            }
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(uiColor)
            ) {
                content()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(bottomHalfColor)
        )
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(130.dp + if (state.isWritingNfc) yDelta else 0.dp)
            .background(tophalfColor)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .offset(y = (-50).dp)
            .align(Alignment.BottomCenter)
            .size(120.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onInputEvent(InputEvent.ToggleNfcWriteMode)
            }
        ) {
            Box(modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(16.dp)
                .clip(CircleShape)
                .background(uiColor)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(180.dp + if (state.isWritingNfc) yDelta else 0.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(120.dp)
                .clip(CircleShape)
                .zIndex(3f)
                .background(backgroundColor)
        ) {
            Box(modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .padding(16.dp)
                .clip(CircleShape)
                .background(bottomHalfColor)
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(if (state.isWritingNfc) yDelta else 0.dp)
            )
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(y = (-125).dp, x = (-25).dp)
                    .height(400.dp)
                    .width(75.dp)
                    .rotate(330f)
                    .background(color = PokeballRed)
                )
                Box(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(y = (-125).dp, x = 25.dp)
                    .height(400.dp)
                    .width(75.dp)
                    .rotate(-330f)
                    .background(color = PokeballRed)
                )
            }
        }
    }
}

@Preview
@Composable
fun PokeScaffoldPreview() {
    PokeballScaffold(
        tophalfColor = GreatballBlue,
        bottomHalfColor = PokeballWhite,
        backgroundColor = PokeballGrey,
        uiColor = ThemeDarkGrey,
        state = LeaderState()
    ) {

    }
}