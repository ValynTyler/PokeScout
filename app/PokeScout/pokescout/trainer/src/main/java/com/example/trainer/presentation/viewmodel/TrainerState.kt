package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.PokemonNfcData
import com.example.trainer.domain.model.EvolutionChain
import com.example.trainer.domain.model.PokemonSpecies

data class TrainerState(
    val nfcData: PokemonNfcData? = null,

    val ancestorData: PokemonSpecies? = null,
    val speciesData: PokemonSpecies? = null,
    val evolutionData: EvolutionChain? = null,

    val isLoading: Boolean = false,
)