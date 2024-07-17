package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.PokemonNfcData
import com.example.trainer.domain.model.PokemonSpecies

data class TrainerState(
    val nfcData: PokemonNfcData? = null,

    val ancestorData: PokemonSpecies? = null,
    val speciesData: PokemonSpecies? = null,
    val evolutionData: List<PokemonSpecies> = emptyList(),

    val isLoading: Boolean = false,
)