package com.example.pokemon.data.repository

import com.example.result.Result
import com.example.pokemon.data.mappers.toChainLink
import com.example.pokemon.data.mappers.toPokemonSpecies
import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.repository.PokemonRepository
import javax.inject.Inject
import kotlin.Exception

class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
) : PokemonRepository {
    override suspend fun getSpeciesById(id: Int): Result<PokemonSpecies, Exception> {
        return try {
            val speciesDto = api.getSpeciesById(id)
            Result.Ok(speciesDto.toPokemonSpecies())

        } catch (e: Exception) {
            Result.Err(e)
        }
    }

    override suspend fun getEvolutionChainById(chainId: Int): Result<EvolutionChain, Exception> {
        return try {
            val chainDto = api.getEvolutionChain(chainId)
            Result.Ok(chainDto.toChainLink(api))

        } catch (e: Exception) {
            Result.Err(e)
        }
    }
}