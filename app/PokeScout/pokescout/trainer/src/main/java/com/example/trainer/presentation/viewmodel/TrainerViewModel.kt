package com.example.trainer.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.domain.PokemonNfcData
import com.example.result.ok
import com.example.trainer.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val repository: PokemonRepository,
) : ViewModel() {

    var state by mutableStateOf(TrainerState())
        private set

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
                    state = state.copy(
                        isLoading = false,
                        speciesData = species,
                        ancestorData = species.evolvesFromId?.let { repository.getSpeciesById(it).ok() },
                        evolutionData = species.evolutionChainId.let { repository.getEvolutionChainById(it).ok() }
                    )
                }
            }
        }
    }
}