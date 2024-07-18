package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData

data class TrainerState(
    val nfcData: PokemonNfcData? = null,

    val ancestorData: PokemonSpecies? = null,
    val speciesData: PokemonSpecies? = null,
    val evolutionData: EvolutionChain? = null,

    val isLoading: Boolean = false,


    val totalEvolutionStages: Int? = null,
    val currentEvolutionStage: Int? = null,
)