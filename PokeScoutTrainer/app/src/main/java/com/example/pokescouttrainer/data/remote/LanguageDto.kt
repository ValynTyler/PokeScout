package com.example.pokescouttrainer.data.remote

import com.squareup.moshi.Json

data class LanguageDto(
    @field:Json(name = "name") val languageName: String,
    @field:Json(name = "url") val languageUrl: String,
)