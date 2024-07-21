package com.example.trainer.presentation.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

object Trainer {
    sealed class State {
        data object Closed : State()
        data class Open(
            val nfcData: PokemonNfcData,
            val apiData: ApiData
        ) : State()
    }

    sealed class ApiData {
        data object Loading: ApiData()
        data object Error: ApiData()
        data class Success(
            val species: PokemonSpecies,
            val evolution: EvolutionChain,
        ): ApiData()
    }

    @HiltViewModel
    class ViewModel @Inject constructor(
        repository: PokemonRepository
    ) {
        var state: State by mutableStateOf(State.Closed)
            private set
    }
}