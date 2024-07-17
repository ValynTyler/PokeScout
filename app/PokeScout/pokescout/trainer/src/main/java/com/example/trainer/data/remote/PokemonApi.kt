package com.example.trainer.data.remote

import com.example.trainer.data.remote.evolution.EvolutionChainDto
import com.example.trainer.data.remote.species.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("/api/v2/pokemon-species/{speciesId}")
    suspend fun getSpeciesById(
        @Path("speciesId") id: Int,
    ): PokemonSpeciesDto

    @GET("/api/v2/evolution-chain/{evolutionChainId}")
    suspend fun getEvolutionChainById(
        @Path("evolutionChainId") id: Int,
    ): EvolutionChainDto
}