package com.example.trainer.data.remote.evolution

import com.squareup.moshi.Json

data class EvolutionChainDto(
    @field:Json(name = "id") val evolutionChainId: Int,
    @field:Json(name = "chain") val rootLink: ChainLinkDto,
)