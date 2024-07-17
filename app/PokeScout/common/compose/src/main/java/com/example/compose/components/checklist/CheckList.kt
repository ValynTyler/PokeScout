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
    items: BooleanArray = booleanArrayOf(),
    onChange: (Int, Boolean) -> Unit = { _, _ ->},
) {
    LazyColumn(
        modifier = modifier
            .background(color)
    ) {
        for (i in items.indices) {
            item {
                CheckListItem(
                    label = "Hello there",
                    checked = items[i],
                    onChange = { checked -> onChange(i, checked) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CheckListPreview() {
    CheckList()
}