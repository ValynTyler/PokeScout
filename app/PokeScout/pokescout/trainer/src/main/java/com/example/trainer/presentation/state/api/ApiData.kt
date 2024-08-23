package com.example.trainer.presentation.state.api

import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies

data class ApiData(
    val species: PokemonSpecies,
    val evolution: EvolutionChain,
)