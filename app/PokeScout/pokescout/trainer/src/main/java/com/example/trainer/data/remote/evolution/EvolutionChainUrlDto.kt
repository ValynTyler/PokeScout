package com.example.trainer.data.remote.evolution

import com.squareup.moshi.Json

data class EvolutionChainUrlDto(
    @field:Json(name = "url") val url: String,
)