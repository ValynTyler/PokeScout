package com.example.trainer.data.remote

import com.squareup.moshi.Json

data class EvolutionChainDto(
    @field:Json(name = "url") val url: String,
)