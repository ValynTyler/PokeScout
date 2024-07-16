package com.example.developer.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.developer.presentation.input.InputEvent
import com.example.pokemon.domain.PokemonNfcData

class DeveloperViewModel : ViewModel() {

    var state by mutableStateOf(DeveloperState())
        private set

    fun readNfcData(data: PokemonNfcData) {
        state = state.copy(
            inputData = state.inputData.copy(
                trainer = data.trainerName,
                species = data.speciesId,
                xp = data.pokemonXp,
            )
        )
    }

    fun processInputEvent(event: InputEvent) {
        state = when (event) {
            is InputEvent.LockEvent -> {
                state.copy(isWritingNfc = !state.isWritingNfc)
            }

            is InputEvent.TextEvent.ChangedName -> {
                val name = event.value.replace("\n", "")
                state.copy(
                    inputData = state.inputData.copy(
                        trainer = name
                    )
                )
            }

            is InputEvent.TextEvent.ChangedId -> {
                val id = event.value.replace("\n", "").toIntOrNull()
                id?.let {
                    if (it > 1025 || it <= 0) {
                        return
                    }
                }
                state.copy(
                    inputData = state.inputData.copy(
                        species = id
                    )
                )
            }

            is InputEvent.TextEvent.ChangedXp -> {
                val xp = event.value.replace("\n", "").toIntOrNull()
                xp?.let {
                    if (it > 999999 || it < 0) {
                        return
                    }
                }
                state.copy(
                    inputData = state.inputData.copy(
                        xp = xp
                    )
                )
            }
        }
    }
}