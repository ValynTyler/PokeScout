package com.example.trainer.domain.repository

import com.example.result.Result
import com.example.trainer.domain.model.EvolutionChain
import com.example.trainer.domain.model.PokemonSpecies

interface PokemonRepository {
    suspend fun getSpeciesById(id: Int): Result<PokemonSpecies, Exception>
    suspend fun getEvolutionChainById(id: Int): Result<EvolutionChain, Exception>
}