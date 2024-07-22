package com.example.pokemon.domain.repository

import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies

interface PokemonRepository {
    suspend fun getSpeciesById(speciesId: Int): Result<PokemonSpecies>
    suspend fun getEvolutionChainById(chainId: Int): Result<EvolutionChain>
}