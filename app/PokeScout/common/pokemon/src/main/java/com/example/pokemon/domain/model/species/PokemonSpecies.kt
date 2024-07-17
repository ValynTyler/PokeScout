package com.example.pokemon.domain.model.species

import com.example.pokemon.domain.model.locale.Language

data class PokemonSpecies(
    val id: Int,
    val alias: String,
    val localNames: Map<Language, String>,

    val evolvesFromId: Int?,
    val evolutionChainId: Int,
) {
    fun name(): String {
        return localNames[Language.ENGLISH]!!
    }
}