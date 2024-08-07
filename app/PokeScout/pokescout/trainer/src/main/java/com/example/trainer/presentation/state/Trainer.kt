package com.example.trainer.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

object Trainer {
    sealed class Event {
        sealed class Menu : Event() {
            data object Toggle : Menu()
            sealed class View : Menu() {
                data object GridPressed : Menu()
                data object ListPressed : Menu()
            }
        }

        sealed class Button : Event() {
            data object LatchPressed : Button()
        }
    }

    sealed class BadgeViewMode {
        data object ListView : BadgeViewMode()
        data object GridView : BadgeViewMode()
    }

    sealed class DrawerState {
        data object BadgeView : DrawerState()
        data object DailyView : DrawerState()
    }

    sealed class ApiData {
        data object Loading : ApiData()
        data object Error : ApiData()
        data class Success(
            val species: PokemonSpecies,
            val evolution: EvolutionChain,
        ) : ApiData()
    }

    sealed class State {
        data object Closed : State()
        data class Open(
            val nfcData: PokemonNfcData,
            val apiData: ApiData,
            val drawerState: DrawerState,
            val badgeViewMode: BadgeViewMode,
        ) : State()
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        private val repository: PokemonRepository
    ) : androidx.lifecycle.ViewModel() {
        var state: State by mutableStateOf(State.Closed)
            private set

        fun onEvent(event: Event) {
            when (state) {
                is State.Closed -> {}
                is State.Open -> {
                    when (event) {
                        Event.Button.LatchPressed -> {
                            enterState(State.Closed)
                        }

                        Event.Menu.Toggle -> {
                            when ((state as State.Open).drawerState) {
                                is DrawerState.BadgeView -> enterDrawerState(DrawerState.DailyView)
                                is DrawerState.DailyView -> enterDrawerState(DrawerState.BadgeView)
                            }
                        }

                        Event.Menu.View.GridPressed -> {
                            enterViewMode(BadgeViewMode.GridView)
                        }

                        Event.Menu.View.ListPressed -> {
                            enterViewMode(BadgeViewMode.ListView)
                        }
                    }
                }
            }
        }

        fun populate(nfcData: PokemonNfcData) {
            enterState(
                State.Open(
                    nfcData = nfcData,
                    apiData = ApiData.Loading,
                    drawerState = DrawerState.BadgeView,
                    badgeViewMode = BadgeViewMode.GridView,
                )
            )
            viewModelScope.launch {
                enterState(
                    State.Open(
                        nfcData = nfcData,
                        apiData = fetchApiData(nfcData),
                        drawerState = DrawerState.BadgeView,
                        badgeViewMode = BadgeViewMode.GridView,
                    )
                )
            }
        }

        private fun enterViewMode(mode: BadgeViewMode) {
            state = when (mode) {
                BadgeViewMode.GridView -> (state as State.Open).copy(badgeViewMode = BadgeViewMode.GridView)
                BadgeViewMode.ListView -> (state as State.Open).copy(badgeViewMode = BadgeViewMode.ListView)
            }
        }

        private fun enterDrawerState(newState: DrawerState) {
            state = when (newState) {
                is DrawerState.BadgeView -> (state as State.Open).copy(drawerState = DrawerState.BadgeView)
                is DrawerState.DailyView -> (state as State.Open).copy(drawerState = DrawerState.DailyView)
            }
        }

        private fun enterState(newState: State) {
            state = when (newState) {
                is State.Closed -> newState
                is State.Open -> newState
            }
        }

        private suspend fun fetchApiData(nfcData: PokemonNfcData): ApiData {
            val species = repository.getSpeciesById(nfcData.speciesId).fold(
                onFailure = { return ApiData.Error },
                onSuccess = { it }
            )

            val evolution = repository.getEvolutionChainById(species.evolutionChainId).fold(
                onFailure = { return ApiData.Error },
                onSuccess = { it }
            )

            return ApiData.Success(
                species,
                evolution
            )
        }
    }
}