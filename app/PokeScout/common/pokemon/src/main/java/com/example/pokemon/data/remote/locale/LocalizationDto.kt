package com.example.pokemon.data.remote.locale

import com.squareup.moshi.Json

data class LocalizationDto(
    @field:Json(name = "language") val language: LanguageDto,
    @field:Json(name = "name") val localName: String,
)