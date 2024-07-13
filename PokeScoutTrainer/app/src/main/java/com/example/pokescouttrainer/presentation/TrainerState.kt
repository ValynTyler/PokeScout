package com.example.pokescouttrainer.presentation

import com.example.pokescouttrainer.domain.model.TagData
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies

data class TrainerState(
    val tagData: TagData? = null,
    val speciesData: PokemonSpecies? = null,
    val isLoading: Boolean = false,
)