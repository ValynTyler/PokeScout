package com.example.developer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.developer.presentation.input.InputEvent
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.result.Result
import com.example.result.ok
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeveloperViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

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
                if (id != null) {
                    if (id > 1025 || id <= 0) {
                        return
                    }
                    state = state.copy(
                        isLoadingEvolution = true,
                    )
                    viewModelScope.launch {
                        state = when (val speciesResult = repository.getSpeciesById(id)) {
                            is Result.Err -> {
                                Log.e("PokeAPI Error", speciesResult.error.message.toString())
                                state
                            }
                            is Result.Ok -> {
                                state.copy(
                                    isLoadingEvolution = false,
                                    inputData = state.inputData.copy(
                                        evolutionChain = speciesResult.value.evolutionChainId
                                    )
                                )
                            }
                        }
                    }
                } else {
                    state = state.copy(
                        inputData = state.inputData.copy(
                            evolutionChain = null
                        )
                    )
                }
                state.copy(
                    inputData = state.inputData.copy(
                        species = id
                    )
                )
            }

            is InputEvent.TextEvent.ChangeEvolutionChain -> {
                val evolutionChain = event.value.replace("\n", "").toIntOrNull()
                if (evolutionChain != null) {
                    if (evolutionChain > 549 || evolutionChain < 0) {
                        return
                    }
                    state = state.copy(
                        isLoadingSpecies = true,
                    )
                    viewModelScope.launch {
                        state = when (val evolutionResult = repository.getEvolutionChainById(evolutionChain)) {
                            is Result.Err -> {
                                Log.e("PokeAPI Error", evolutionResult.error.message.toString())
                                state
                            }
                            is Result.Ok -> {
                                state.copy(
                                    isLoadingSpecies = false,
                                    inputData = state.inputData.copy(
                                        species = evolutionResult.value.chainRoot.species.id
                                    )
                                )
                            }
                        }
                    }
                } else {
                    state = state.copy(
                        inputData = state.inputData.copy(
                            species = null
                        )
                    )
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