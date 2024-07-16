package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.PokemonNfcData
import com.example.trainer.domain.model.PokemonSpecies

data class TrainerState(
    val nfcData: PokemonNfcData? = null,
    val speciesData: PokemonSpecies? = null,
    val isLoading: Boolean = false,
)