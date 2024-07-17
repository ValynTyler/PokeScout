package com.example.pokemon.data.remote.evolution

import com.example.pokemon.data.remote.species.SpeciesUrlDto
import com.squareup.moshi.Json

data class ChainLinkDto(
    @field:Json(name = "species") val speciesUrl: SpeciesUrlDto,
    @field:Json(name = "evolves_to") val evolvesTo: List<ChainLinkDto>,
)