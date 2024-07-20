package com.example.leader.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leader.presentation.events.InputEvent
import com.example.option.Option
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    var state by mutableStateOf(LeaderState())
        private set

    fun updateNfcData(data: PokemonNfcData) {
        state = state.copy(
            currentNfcData = data
        )
        onInputEvent(InputEvent.SelectScreen(LeaderScreenType.SelectScreen))
    }

    fun clearNfcData() {
        state = state.copy(
            currentNfcData = null
        )
    }

    fun onInputEvent(event: InputEvent) {
        when (event) {
            is InputEvent.TogglePokeball -> handleButtonPress()
            is InputEvent.SelectScreen -> {
                state = state.copy(activeScreenType = event.screen)
            }

            is InputEvent.ScreenEvent.ValorScreen.DayIndexSelectionChange -> state = state.copy(
                valorScreenState = state.valorScreenState.copy(
                    dayIndexSelection = event.newIndex
                )
            )

            is InputEvent.ScreenEvent.GymScreen.GymIndexSelectionChange -> state = state.copy(
                gymScreenState = state.gymScreenState.copy(
                    gymIndexSelection = event.newIndex
                )
            )

            is InputEvent.ScreenEvent.GymScreen.GroupDropdownSelectionChange -> state = state.copy(
                gymScreenState = state.gymScreenState.copy(
                    groupTypeSelection = event.newGroup
                )
            )

            is InputEvent.ScreenEvent.InitScreen.GroupDropdownSelectionChange -> state = state.copy(
                infoScreenState = state.infoScreenState.copy(
                    groupTypeSelection = event.newGroup
                )
            )

            is InputEvent.ScreenEvent.InitScreen.PokemonIdChange -> {
                val id = event.newId.replace("\n", "").toIntOrNull()
                if (id == null) {
                } else if (id > 1025 || id <= 0) {
                } else {
                    state = state.copy(
                        infoScreenState = state.infoScreenState.copy(
                            pokemonIdField = id.toString(),
                        )
                    )
                }
            }

            is InputEvent.ScreenEvent.InitScreen.TrainerNameChange -> {
                state = state.copy(
                    infoScreenState = state.infoScreenState.copy(
                        trainerNameField = event.newName.replace("\n", "")
                    )
                )
            }
        }
    }

    private fun handleButtonPress() {
        when (state.activeScreenType) {
            is LeaderScreenType.InitScreen -> {
                state = state.copy(
                    activeScreenType = LeaderScreenType.LoadingScreen
                )
                viewModelScope.launch {
                    val id = state.infoScreenState.pokemonIdField.toIntOrNull() ?: 0
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
                        isClosed = !state.isClosed,
                        activeScreenType = LeaderScreenType.InitScreen,
                        infoScreenState = state.infoScreenState.copy(
                            currentSpecies = speciesOption,
                            currentEvolutionChain = evolutionChainOption,
                        ),
                    )
                }
                state
            }

            LeaderScreenType.GymScreen -> {

            }

            LeaderScreenType.ValorScreen -> {
                if (state.currentNfcData != null) {
                    val newDailyPoints = state.currentNfcData!!.dailyPoints.clone()
                    val idx = state.valorScreenState.dayIndexSelection
                    if (newDailyPoints[idx] < 4) {
                        newDailyPoints[idx]++
                    }
                    state = state.copy(
                        currentNfcData = state.currentNfcData!!.copy(
                            dailyPoints = newDailyPoints
                        )
                    )
                }
                state = state.copy(isClosed = !state.isClosed)
            }

            LeaderScreenType.LoadingScreen -> {}
            LeaderScreenType.SelectScreen -> {}
            LeaderScreenType.ScanScreen -> {
//                state = state.copy(
//                    isClosed = !state.isClosed
//                )
            }
        }
    }
}