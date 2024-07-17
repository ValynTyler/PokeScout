package com.example.trainer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.scaffold.PokeScoutScaffold
import com.example.compose.theme.PokeScoutTheme
import com.example.trainer.presentation.ui.BadgeDrawer
import com.example.trainer.presentation.ui.PokemonImage
import com.example.trainer.presentation.viewmodel.TrainerState

@Composable
fun MainView(
    state: TrainerState
) {
    PokeScoutTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ModalNavigationDrawer(
                drawerContent = { BadgeDrawer() }
            ) {
                PokemonImage(id = 0)
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(TrainerState())
}