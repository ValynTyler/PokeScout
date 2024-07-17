package com.example.compose.components.checklist

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CheckList(
    modifier: Modifier = Modifier,
    color: Color = Color.Transparent,
) {
    LazyColumn(
        modifier = modifier
            .background(color)
    ) {
        repeat(12) {
            item { CheckListItem("Hello there") }
        }
    }
}

@Preview
@Composable
fun CheckListPreview() {
    CheckList()
}