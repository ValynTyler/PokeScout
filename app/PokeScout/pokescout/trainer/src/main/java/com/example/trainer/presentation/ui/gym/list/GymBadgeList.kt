package com.example.trainer.presentation.ui.gym.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.domain.model.group.TrainerGroup
import com.example.pokemon.domain.model.group.TrainerGroup.filterGyms

@Preview
@Composable
fun GymBadgeList() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        for (item in TrainerGroup.Type.Intermediate.filterGyms()) {
            GymBadgeListItem(gym = item)
        }
    }
}