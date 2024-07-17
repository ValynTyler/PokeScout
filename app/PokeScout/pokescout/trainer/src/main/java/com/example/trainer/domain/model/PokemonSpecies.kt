package com.example.trainer.domain.model

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