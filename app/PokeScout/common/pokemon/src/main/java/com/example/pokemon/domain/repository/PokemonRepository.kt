package com.example.pokemon.domain.repository

import com.example.result.Result
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies

interface PokemonRepository {
    suspend fun getSpeciesById(id: Int): Result<PokemonSpecies, Exception>
    suspend fun getEvolutionChainById(id: Int): Result<EvolutionChain, Exception>
}