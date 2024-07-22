package com.example.pokemon.data.repository

import com.example.pokemon.data.mappers.toChainLink
import com.example.pokemon.data.mappers.toPokemonSpecies
import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
) : PokemonRepository {
    override suspend fun getSpeciesById(id: Int): Result<PokemonSpecies> {
        return try {
            val speciesDto = api.getSpeciesById(id)
            Result.success(speciesDto.toPokemonSpecies())

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEvolutionChainById(id: Int): Result<EvolutionChain> {
        return try {
            val chainDto = api.getEvolutionChain(id)
            Result.success(chainDto.toChainLink(api))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}