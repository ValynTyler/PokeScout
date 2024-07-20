package com.example.leader.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.pokefontPixel
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.ui.BackButton
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.domain.model.GroupType

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ValorScreen(
    state: LeaderState = LeaderState(),
    onInputEvent: (InputEvent) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(
            modifier = Modifier
                .height(60.dp)
                .padding(16.dp)
        )

        Text(
            text = "Valorificare", fontSize = 16.sp, color = PokeballWhite,
            modifier = Modifier.padding(16.dp)
        )

        Box {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = "Ziua ${state.valorScreenState.dayIndexSelection + 1}",
                    onValueChange = {},
                    label = { Text(fontFamily = pokefontPixel, text = "ZI") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    for (i in 0..<4) {
                        DropdownMenuItem(
                            text = { Text(fontFamily = pokefontPixel, text = "Ziua ${i + 1}") },
                            onClick = {
                                expanded = false
                                onInputEvent(
                                    InputEvent.ScreenEvent.ValorScreen.DayIndexSelectionChange(i)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
    BackButton { onInputEvent(InputEvent.SelectScreen(LeaderScreenType.SelectScreen)) }
}