package com.example.trainer.presentation.state.api

import com.example.pokemon.domain.model.species.PokemonSpecies

sealed class ApiStatus {
    data object Error: ApiStatus()
    data object Loading: ApiStatus()
    data class Success(val data: ApiData): ApiStatus()
}