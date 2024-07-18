package com.example.trainer.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.trainer.presentation.ui.BadgeDrawer
import com.example.trainer.presentation.ui.PokeballButton
import com.example.trainer.presentation.ui.PokeballTop
import com.example.trainer.presentation.ui.PokemonImagePreview
import com.example.trainer.presentation.ui.StatBox
import com.example.trainer.presentation.viewmodel.TrainerState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    state: TrainerState
) {
    PokeScoutTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            ModalNavigationDrawer(
                drawerContent = { BadgeDrawer() }
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        PokeballTop()
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            PokemonImagePreview(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(color = MaterialTheme.colorScheme.secondaryContainer)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth().height(8.dp),
                                progress = state.nfcData?.xp()?.div(1800f) ?: 0f)
                            Spacer(modifier = Modifier.height(16.dp))
                            StatBox(
                                state,
                                modifier = Modifier
                                    .background(color = ThemeDarkGrey)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
                PokeballButton()
                Box(
                    Modifier.fillMaxSize()
                ) {Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(116.dp)
                        .background(color = PokeballGrey)
                )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(color = PokeballWhite)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = (-50).dp)
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(color = PokeballGrey),
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .clip(CircleShape)
                            .background(color = ThemeDarkGrey))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(TrainerState())
}