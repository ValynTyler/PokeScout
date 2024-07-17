package com.example.compose.components.checklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckListItem(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    onChange: (Boolean) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = modifier.padding(start = 8.dp)
        )
        Checkbox(
            enabled = enabled,
            checked = checked,
            onCheckedChange = onChange,
        )
    }
}

@Preview
@Composable
fun CheckListItemPreview() {
    CheckListItem("ITEM ITEM")
}