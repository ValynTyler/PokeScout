package com.example.pokescouttrainer.data.remote

import com.squareup.moshi.Json

data class LocalizationDto(
    @field:Json(name = "language") val language: LanguageDto,
    @field:Json(name = "name") val localName: String,
)