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
    sealed class BadgeViewMode {
        data object ListView: BadgeViewMode()
        data object GridView: BadgeViewMode()
    }

    sealed class DrawerState {
        data object Closed: DrawerState()
        sealed class Open: DrawerState() {
            data class BadgeView(var viewMode: BadgeViewMode): DrawerState()
            data object DailyView: DrawerState()
        }
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
        ) : State()
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        private val repository: PokemonRepository
    ) : androidx.lifecycle.ViewModel() {
        var state: State by mutableStateOf(State.Closed)
            private set

        fun onClicked() {
            when (state) {
                State.Closed -> {}
                is State.Open -> { enterState(State.Closed) }
            }
        }

        fun populate(nfcData: PokemonNfcData) {
            enterState(State.Open(nfcData = nfcData, apiData = ApiData.Loading, drawerState = DrawerState.Closed))
            viewModelScope.launch {
                enterState(State.Open(nfcData = nfcData, apiData = fetchApiData(nfcData), drawerState = DrawerState.Closed))
            }
        }

        private fun enterState(newState: State) {
            state = when (newState) {
                State.Closed -> newState
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