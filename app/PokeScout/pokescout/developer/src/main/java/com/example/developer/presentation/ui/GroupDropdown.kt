package com.example.developer.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemon.domain.model.GroupType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupDropdown(
    type: GroupType,
    modifier: Modifier = Modifier,
    onChanged: (GroupType) -> Unit = {},
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = type.toString(),
            onValueChange = {},
            label = { Text(text = "Group Type") },
            trailingIcon = {
                TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = "Beginner") },
                onClick = {
                    expanded = false
                    onChanged(GroupType.Beginner)
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Intermediate") },
                onClick = {
                    expanded = false
                    onChanged(GroupType.Intermediate)
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Advanced") },
                onClick = {
                    expanded = false
                    onChanged(GroupType.Advanced)
                }
            )
        }
    }
}

@Preview
@Composable
fun GroupDropdownPreview() {
    GroupDropdown(GroupType.Advanced)
}