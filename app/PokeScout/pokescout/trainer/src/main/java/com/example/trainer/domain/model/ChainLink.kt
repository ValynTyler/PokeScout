package com.example.trainer.domain.model

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
)