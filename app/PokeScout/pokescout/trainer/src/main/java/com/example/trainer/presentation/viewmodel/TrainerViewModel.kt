package com.example.trainer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.result.Result
import com.example.result.ok
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    var state by mutableStateOf(TrainerState())
        private set

    fun onClicked() {
        state = state.copy(isOpen = !state.isOpen)
    }

    fun readNfcData(data: PokemonNfcData) {
        state = state.copy(
            nfcData = data
        )
        loadSpecies()
    }

    private fun loadSpecies() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
            )

            state.nfcData?.let { data ->
                repository.getSpeciesById(data.speciesId).ok()?.let { species ->

                    val evolutionChain = when (val result =
                        repository.getEvolutionChainById(species.evolutionChainId)) {
                        is Result.Err -> throw result.error
                        is Result.Ok -> result.value
                    }

                    val ancestor = species.evolvesFromId?.let {
                        when (val result = repository.getSpeciesById(species.evolvesFromId!!)) {
                            is Result.Err -> throw result.error
                            is Result.Ok -> result.value
                        }
                    }

                    state = state.copy(
                        isLoading = false,
                        speciesData = species,
                        ancestorData = ancestor,
                        evolutionData = evolutionChain,
                    )

                    Log.e("EEEE", evolutionChain.findLinkById(species.id).toString())
                }
            }
        }
    }
}