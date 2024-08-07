package com.example.trainer.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.pokemon.presentation.theme.PokeBallGrey
import com.example.trainer.R

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    resourceId: Int = R.drawable.badge,
    isSelected: Boolean = true,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    Image(
        bitmap = ImageBitmap.imageResource(resourceId),
        contentDescription = null,
        filterQuality = FilterQuality.None,
        colorFilter = if (!isSelected) ColorFilter.tint(PokeBallGrey, BlendMode.SrcIn) else null,
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            ),
    )
}