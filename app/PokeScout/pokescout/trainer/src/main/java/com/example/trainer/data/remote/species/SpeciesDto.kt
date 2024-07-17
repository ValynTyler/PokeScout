package com.example.trainer.data.remote.species

import com.example.trainer.data.remote.locale.LocalizationDto
import com.example.trainer.data.remote.evolution.EvolutionChainUrlDto
import com.squareup.moshi.Json

data class SpeciesDto(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val alias: String,
    @field:Json(name = "names") val localNames: List<LocalizationDto>,
    @field:Json(name = "evolves_from_species") val evolvesFromSpeciesUrl: SpeciesUrlDto?,
    @field:Json(name = "evolution_chain") val evolutionChainUrl: EvolutionChainUrlDto,
)