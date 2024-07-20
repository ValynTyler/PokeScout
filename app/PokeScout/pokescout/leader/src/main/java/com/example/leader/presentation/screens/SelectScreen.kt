package com.example.leader.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballWhite
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.ui.BackButton
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.leader.presentation.viewmodel.LeaderState

@Preview
@Composable
fun SelectScreen(
    onInputEvent: (InputEvent) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(60.dp)
                .padding(16.dp)
        )
        val cardNames = listOf(
            Pair("Completare GYM", LeaderScreenType.GymScreen),
            Pair("Valorificare Trainer", LeaderScreenType.ValorScreen),
            Pair("Resetare date\n(PERMANENT)", LeaderScreenType.InitScreen)
        )
        for (item in cardNames) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(16.dp)
                    .background(PokeballGrey)
                    .clickable { onInputEvent(InputEvent.SelectScreen(item.second)) }
            ) {
                Text(text = item.first, color = PokeballWhite, modifier = Modifier.padding(16.dp))
            }
        }
    }
    BackButton() {
        onInputEvent(InputEvent.SelectScreen(LeaderScreenType.ScanScreen))
    }
}