package com.example.trainer.domain.model.evolution

import com.example.trainer.domain.model.species.PokemonSpecies

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
)