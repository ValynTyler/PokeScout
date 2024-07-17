package com.example.trainer.data.remote

import com.example.trainer.data.remote.evolution.EvolutionChainDto
import com.example.trainer.data.remote.species.SpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("/api/v2/pokemon-species/{speciesId}")
    suspend fun getSpeciesById(
        @Path("speciesId") id: Int,
    ): SpeciesDto

    @GET("/api/v2/pokemon-species/{speciesAlias}")
    suspend fun getSpeciesByAlias(
        @Path("speciesAlias") alias: String,
    ): SpeciesDto

    @GET("/api/v2/evolution-chain/{evolutionChainId}")
    suspend fun getEvolutionChain(
        @Path("evolutionChainId") id: Int,
    ): EvolutionChainDto
}