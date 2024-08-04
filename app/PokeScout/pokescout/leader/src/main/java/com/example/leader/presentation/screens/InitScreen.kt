package com.example.leader.presentation.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.theme.PokeballWhite
import com.example.pokemon.presentation.theme.pokefontPixel
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.domain.model.GroupType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun InitScreen(
    state: LeaderState = LeaderState(),
    onInputEvent: (InputEvent) -> Unit = {},
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .height(60.dp)
                    .padding(16.dp)
            )
            Text(
                text = "Intrare Date", fontSize = 16.sp, color = PokeballWhite,
                modifier = Modifier.padding(16.dp)
            )
            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                value = state.infoScreenState.trainerNameField,
                label = { Text("Nume Trainer") },
                onValueChange = {
                    onInputEvent(
                        InputEvent.ScreenEvent.InitScreen.TrainerNameChange(
                            it
                        )
                    )
                }
            )
            TextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                value = state.infoScreenState.pokemonIdField,
                label = { Text("ID Pokemon") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                onValueChange = { onInputEvent(InputEvent.ScreenEvent.InitScreen.PokemonIdChange(it)) }
            )

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = when (state.infoScreenState.groupTypeSelection) {
                        GroupType.Beginner -> "Lupisor"
                        GroupType.Intermediate -> "Temerar"
                        GroupType.Advanced -> "Explorator"
                    },
                    onValueChange = {},
                    label = { Text(fontFamily = pokefontPixel, text = "Grupa de Varsta") },
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
                        text = { Text(fontFamily = pokefontPixel, text = "Lupisor") },
                        onClick = {
                            expanded = false
                            onInputEvent(
                                InputEvent.ScreenEvent.InitScreen.GroupDropdownSelectionChange(
                                    GroupType.Beginner
                                )
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(fontFamily = pokefontPixel, text = "Temerar") },
                        onClick = {
                            expanded = false
                            onInputEvent(
                                InputEvent.ScreenEvent.InitScreen.GroupDropdownSelectionChange(
                                    GroupType.Intermediate
                                )
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(fontFamily = pokefontPixel, text = "Explorator") },
                        onClick = {
                            expanded = false
                            onInputEvent(
                                InputEvent.ScreenEvent.InitScreen.GroupDropdownSelectionChange(
                                    GroupType.Advanced
                                )
                            )
                        }
                    )
                }
            }
        }
        Box(
            Modifier
                .padding(16.dp)
                .clickable { onInputEvent(InputEvent.SelectScreen(LeaderScreenType.SelectScreen)) }
        ) {
            Text(
                "<",
                fontSize = 50.sp,
                textAlign = TextAlign.Start,
                fontFamily = pokefontPixel,
                modifier = Modifier.fillMaxWidth(),
                color = PokeballWhite
            )
        }
    }
}