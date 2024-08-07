package com.example.trainer.presentation.screens.display

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallDarkGrey
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.pokemon.presentation.theme.PokeBallWhite
import com.example.trainer.R
import com.example.trainer.presentation.state.Trainer
import com.example.trainer.presentation.ui.MenuButton
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
        drawerContainerColor = PokeBallGrey,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(start = 12.dp)
        ) {
            // Content
            when (drawerState) {
                is Trainer.DrawerState.DailyView -> Box(modifier = Modifier.weight(1f).fillMaxWidth().background(PokeBallDarkGrey))
                is Trainer.DrawerState.BadgeView -> {
                    GymBadgeDisplay(
                        nfc = nfc,
                        viewMode = badgeViewMode,
                        modifier = Modifier
                            .weight(1f)
                            .background(PokeBallDarkGrey)
                            .padding(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Menu
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(PokeBallDarkGrey)
                    .padding(horizontal = 12.dp)
            ) {
                // BadgeMenu
                when (drawerState) {
                    is Trainer.DrawerState.BadgeView -> Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        MenuButton(
                            modifier = Modifier.size(32.dp),
                            resourceId = R.drawable.grid,
                            isSelected = when (badgeViewMode) {
                                Trainer.BadgeViewMode.GridView -> true
                                Trainer.BadgeViewMode.ListView -> false
                            }
                        ) { onPressed(Trainer.Event.Menu.View.GridPressed) }
                        MenuButton(
                            modifier = Modifier.size(32.dp),
                            resourceId = R.drawable.list,
                            isSelected = when (badgeViewMode) {
                                Trainer.BadgeViewMode.GridView -> false
                                Trainer.BadgeViewMode.ListView -> true
                            }
                        ) { onPressed(Trainer.Event.Menu.View.ListPressed) }
                    }

                    is Trainer.DrawerState.DailyView -> {}
                }

                Spacer(modifier = Modifier)

                // Toggle Button
                MenuButton(
                    resourceId = when (drawerState) {
                        is Trainer.DrawerState.BadgeView -> R.drawable.badge
                        is Trainer.DrawerState.DailyView -> R.drawable.clock
                    },
                    modifier = Modifier.size(32.dp)
                ) { onPressed(Trainer.Event.Menu.Toggle) }
            }
        }
    }
}