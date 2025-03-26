package com.example.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.common.model.GymType
import com.example.common.ui.image.gym.GymBadgeImage

@Composable
fun GymBadgeListItem(
    gymType: GymType,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        GymBadgeImage(
            gymType,
            modifier = Modifier.weight(1f),
            contentScale = ContentScale.FillWidth,
        )
        Text(
            text = gymType.toString(),
            modifier = Modifier.weight(4f),
            textAlign = TextAlign.End,
            style = TextStyle(
                fontSize = 48.sp,
                color = Color.White,
            )
        )
    }
}

@Preview
@Composable
private fun GymBadgeListItemPreview() {
    GymBadgeListItem(
        GymType.PokeCulinaria,
        Modifier.fillMaxSize()
    )
}