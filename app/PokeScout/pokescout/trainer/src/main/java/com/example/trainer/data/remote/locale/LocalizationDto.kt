package com.example.trainer.data.remote.locale

import com.example.trainer.data.remote.locale.LanguageDto
import com.squareup.moshi.Json

data class LocalizationDto(
    @field:Json(name = "language") val language: LanguageDto,
    @field:Json(name = "name") val localName: String,
)