package com.example.trainer.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.trainer.presentation.ui.BadgeDrawer
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
                            StatBox(state)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .background(color = PokeballWhite)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 60.dp)
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(color = PokeballGrey)
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = 45.dp)
                            .size(75.dp)
                            .clip(CircleShape)
                            .background(color = PokeballWhite)
                    )
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