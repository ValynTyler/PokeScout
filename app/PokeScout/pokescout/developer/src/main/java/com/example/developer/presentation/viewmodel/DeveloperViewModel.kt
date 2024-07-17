package com.example.developer.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.developer.presentation.input.InputEvent
import com.example.pokemon.domain.nfc.PokemonNfcData

class DeveloperViewModel : ViewModel() {

    var state by mutableStateOf(DeveloperState())
        private set

    fun readNfcData(data: PokemonNfcData) {
        state = state.copy(
            inputData = state.inputData.copy(
                trainer = data.trainerName,
                species = data.speciesId,
                evolutionChain = data.evolutionChainId,
                // TODO
            )
        )
    }

    fun processInputEvent(event: InputEvent) {
        state = when (event) {
            is InputEvent.LockEvent -> {
                state.copy(isWritingNfc = !state.isWritingNfc)
            }

            is InputEvent.TextEvent.ChangeTrainer -> {
                val name = event.value.replace("\n", "")
                state.copy(
                    inputData = state.inputData.copy(
                        trainer = name
                    )
                )
            }

            is InputEvent.TextEvent.ChangeSpecies -> {
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

            is InputEvent.TextEvent.ChangeEvolutionChain -> {
                val evolutionChain = event.value.replace("\n", "").toIntOrNull()
                evolutionChain?.let {
                    if (it > 549 || it < 0) {
                        return
                    }
                }
                state.copy(
                    inputData = state.inputData.copy(
                        evolutionChain = evolutionChain
                    )
                )
            }
        }
    }
}