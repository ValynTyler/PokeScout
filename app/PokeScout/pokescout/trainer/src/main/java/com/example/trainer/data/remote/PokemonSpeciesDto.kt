package com.example.trainer.data.remote

import com.squareup.moshi.Json

data class PokemonSpeciesDto(
    @field:Json(name = "id") val speciesId: Int,
    @field:Json(name = "name") val speciesAlias: String,
    @field:Json(name = "names") val speciesLocalNames: List<LocalizationDto>
)