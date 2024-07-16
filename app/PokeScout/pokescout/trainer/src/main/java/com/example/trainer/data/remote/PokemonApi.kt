package com.example.trainer.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("/api/v2/pokemon-species/{speciesId}")
    suspend fun getSpeciesById(
        @Path("speciesId") id: Int,
    ): PokemonSpeciesDto
}