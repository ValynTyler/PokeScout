package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.input.checklist.CheckList
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun BadgeList(
    state: DeveloperState,
    onChange: (Int, Boolean) -> Unit = { _, _ ->},
) {
    CheckList(
        enabled = !state.isWritingNfc,
        items = state.inputData.gymBadges.dropLast(20).toBooleanArray(),
        color = MaterialTheme.colorScheme.primaryContainer,
        onChange = onChange,
        modifier = Modifier
            .height(240.dp)
            .padding(8.dp)
    )
}

@Preview
@Composable
fun BadgeListPreview() {
    BadgeList(DeveloperState())
}