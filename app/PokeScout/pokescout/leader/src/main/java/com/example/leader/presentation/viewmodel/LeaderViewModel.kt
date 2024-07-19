package com.example.leader.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leader.presentation.events.InputEvent
import com.example.option.Option
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderViewModel @Inject constructor(
    val repository: PokemonRepository
) : ViewModel() {
    var state by mutableStateOf(LeaderState())
        private set

    fun onInputEvent(event: InputEvent) {
        state = when (event) {
            is InputEvent.ToggleNfcWriteMode -> {
                if (!state.isWritingNfc) {
                    state = state.copy(isLoading = true)
                    viewModelScope.launch {
                        val id = state.pokemonIdField.toIntOrNull() ?: 0
                        val speciesOption =
                            when (val speciesResult = repository.getSpeciesById(id)) {
                                is Result.Err -> {
                                    Log.e(
                                        "Pokemon API ERROR",
                                        speciesResult.error.message.toString()
                                    )
                                    Option.None(Unit)
                                }

                                is Result.Ok -> {
                                    Option.Some(speciesResult.value)
                                }
                            }

                        val evolutionChainOption = when (speciesOption) {
                            is Option.None -> Option.None(Unit)
                            is Option.Some -> {
                                when (val evolutionChainResult =
                                    repository.getEvolutionChainById(speciesOption.value.evolutionChainId)) {
                                    is Result.Err -> Option.None(Unit)
                                    is Result.Ok -> Option.Some(evolutionChainResult.value)
                                }
                            }
                        }

                        state = state.copy(
                            isLoading = false,
                            currentSpecies = speciesOption,
                            currentEvolutionChain = evolutionChainOption,
                        )
                    }
                    state.copy(isWritingNfc = true)
                } else {
                    state.copy(isWritingNfc = false)
                }
            }
            is InputEvent.GroupDropdownSelectionChange -> state.copy(groupDropdownSelection = event.newGroup)
            is InputEvent.TrainerNameChange -> state.copy(trainerNameField = event.newName.replace("\n", ""))
            is InputEvent.PokemonIdChange -> {
                val id = event.newId.replace("\n", "").toIntOrNull()
                if (id == null) {
                    state.copy(pokemonIdField = "")
                } else if (id > 1025 || id <= 0) {
                    state
                } else {
                    state.copy(
                        pokemonIdField = id.toString(),
                    )
                }
            }
        }
    }
}