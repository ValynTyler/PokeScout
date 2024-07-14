package com.example.pokescouttrainer.presentation

import com.example.pokescouttrainer.domain.nfc.PokemonNfcData
import com.example.pokescouttrainer.domain.species.PokemonSpecies

data class TrainerState(
    val nfcData: PokemonNfcData? = null,
    val speciesData: PokemonSpecies? = null,
    val isLoading: Boolean = false,
)