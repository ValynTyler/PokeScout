package com.example.leader.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.compose.theme.pokefontPixel
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.domain.model.GroupType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitScreen(
    state: LeaderState,
    onInputEvent: (InputEvent) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                Spacer(modifier = Modifier.height(80.dp))
                TextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = state.trainerNameField,
                    label = { Text("Trainer name") },
                    onValueChange = { onInputEvent(InputEvent.TrainerNameChange(it)) }
                )
                TextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    value = state.pokemonIdField,
                    label = { Text("Pokemon ID") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    onValueChange = { onInputEvent(InputEvent.PokemonIdChange(it)) }
                )
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = state.groupDropdownSelection.toString(),
                        onValueChange = {},
                        label = { Text(fontFamily = pokefontPixel, text = "Group") },
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
                        onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text(fontFamily = pokefontPixel, text = "Beginner") },
                            onClick = {
                                expanded = false
                                onInputEvent(
                                    InputEvent.GroupDropdownSelectionChange(
                                        GroupType.Beginner
                                    )
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(fontFamily = pokefontPixel, text = "Intermediate") },
                            onClick = {
                                expanded = false
                                onInputEvent(
                                    InputEvent.GroupDropdownSelectionChange(
                                        GroupType.Intermediate
                                    )
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Advanced") },
                            onClick = {
                                expanded = false
                                onInputEvent(
                                    InputEvent.GroupDropdownSelectionChange(
                                        GroupType.Advanced
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}