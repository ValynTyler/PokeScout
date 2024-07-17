package com.example.trainer.data.remote.evolution

import com.example.trainer.data.remote.species.PokemonSpeciesUrlDto
import com.squareup.moshi.Json

data class ChainLinkDto(
    @field:Json(name = "species") val speciesUrl: PokemonSpeciesUrlDto,
    @field:Json(name = "evolves_to") val evolvesTo: ChainLinkDto?,
)