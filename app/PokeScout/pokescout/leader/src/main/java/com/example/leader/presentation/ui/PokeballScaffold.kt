package com.example.leader.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.GreatballBlue
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey

@Composable
fun PokeballScaffold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(color = GreatballBlue)
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            content()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = PokeballWhite)
            )
        }
    }
    PokeballButton(
        PokeballGrey,
        PokeballWhite,
        Alignment.TopCenter,
        60.dp
    )
    PokeballButton(
        PokeballGrey,
        ThemeDarkGrey,
        Alignment.BottomCenter,
        (-60).dp
    )
}

@Preview
@Composable
fun PokeballScaffoldPreview() {
    Box(modifier = Modifier.fillMaxSize()) {
        PokeballScaffold()
    }
}