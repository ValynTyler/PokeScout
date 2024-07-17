package com.example.trainer.data.remote

import com.squareup.moshi.Json

data class PokemonSpeciesUrlDto(
    @field:Json(name = "name") val speciesName: String,
    @field:Json(name = "url") val speciesUrl: String,
)