package com.example.trainer.presentation.ui.gym

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.trainer.R
import com.example.trainer.presentation.ui.gym.grid.GymBadgeGrid
import com.example.trainer.presentation.ui.gym.list.GymBadgeList

@Composable
fun GymBadgeDisplay(nfc: PokemonNfcData) {
    var isGrid by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp, horizontal = 24.dp)
            .padding(start = 24.dp)
    ) {
        if (isGrid) {
            GymBadgeGrid(nfc = nfc)
        } else {
            GymBadgeList(nfc = nfc)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .padding(start = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            val iconSize = 32.dp
            val interactionSource = remember { MutableInteractionSource() }
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.grid),
                contentDescription = null,
                filterQuality = FilterQuality.None,
                colorFilter = if (!isGrid) ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn) else null,
                modifier = Modifier
                    .size(iconSize)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { isGrid = true },
            )
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.list),
                contentDescription = null,
                filterQuality = FilterQuality.None,
                colorFilter = if (isGrid) ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn) else null,
                modifier = Modifier
                    .size(iconSize)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { isGrid = false },
            )
        }
    }
}