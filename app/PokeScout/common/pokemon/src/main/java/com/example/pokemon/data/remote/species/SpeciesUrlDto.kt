package com.example.pokemon.data.remote.species

import com.squareup.moshi.Json

data class SpeciesUrlDto(
    @field:Json(name = "name") val alias: String,
    @field:Json(name = "url") val url: String,
)