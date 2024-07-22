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
            val apiData: ApiData
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
            enterState(State.Open(nfcData = nfcData, ApiData.Loading))
            viewModelScope.launch {
                enterState(State.Open(nfcData = nfcData, fetchApiData(nfcData)))
            }
        }

        private suspend fun fetchApiData(nfcData: PokemonNfcData): ApiData {
            val species = repository.getSpeciesById(nfcData.speciesId).fold(
                onFailure = { return ApiData.Error },
                onSuccess = { it }
            )

            val evolution = repository.getEvolutionChainById(species.id).fold(
                onFailure = { return ApiData.Error },
                onSuccess = { it }
            )

            return ApiData.Success(
                species,
                evolution
            )
        }

        private fun enterState(newState: State) {
            state = when (newState) {
                State.Closed -> newState
                is State.Open -> newState
            }
        }
    }
}