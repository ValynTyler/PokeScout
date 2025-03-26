package com.example.common.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.example.common.model.GymType
import com.example.common.ui.image.gym.GymBadgeImage

@Composable
fun GymBadgeGridItem(
    gymType: GymType,
    modifier: Modifier = Modifier,
) {
    GymBadgeImage(
        gymType,
        modifier,
        contentScale = ContentScale.FillWidth
    )
}

@Preview
@Composable
private fun GymBadgeGridItemPreview() {
    GymBadgeGridItem(
        GymType.Parkour,
        Modifier.fillMaxSize()
    )
}