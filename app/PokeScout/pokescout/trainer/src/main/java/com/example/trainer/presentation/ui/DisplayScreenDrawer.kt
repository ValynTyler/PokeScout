package com.example.trainer.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.trainer.R
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.gym.GymBadgeDisplay

@Composable
fun DisplayScreenDrawer(
    nfc: PokemonNfcData,
    drawerState: Trainer.DrawerState,
    badgeViewMode: Trainer.BadgeViewMode,
    onPressed: (Trainer.Event.Menu) -> Unit,
) {
    ModalDrawerSheet(
        drawerShape = RectangleShape,
        drawerContainerColor = PokeBallDarkGrey,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 24.dp)
    ) {
        Box {
            // Content
            when (drawerState) {
                is Trainer.DrawerState.DailyView -> {}
                is Trainer.DrawerState.BadgeView -> {
                    GymBadgeDisplay(nfc = nfc, viewMode = badgeViewMode) { onPressed(it) }
                }
            }

            // Toggle Button
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .padding(start = 24.dp)
            ) {
                MenuButton(
                    resourceId = when (drawerState) {
                        is Trainer.DrawerState.BadgeView -> R.drawable.badge
                        is Trainer.DrawerState.DailyView -> R.drawable.clock
                    },
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd),
                ) { onPressed(Trainer.Event.Menu.Toggle) }
            }
        }
    }
}