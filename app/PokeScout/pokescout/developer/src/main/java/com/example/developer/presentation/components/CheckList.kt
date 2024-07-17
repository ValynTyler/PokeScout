package com.example.developer.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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