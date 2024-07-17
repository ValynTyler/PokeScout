package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.trainer.domain.model.evolution.EvolutionChain
import com.example.trainer.domain.model.species.PokemonSpecies

data class TrainerState(
    val nfcData: PokemonNfcData? = null,

    val ancestorData: PokemonSpecies? = null,
    val speciesData: PokemonSpecies? = null,
    val evolutionData: EvolutionChain? = null,

    val isLoading: Boolean = false,
)