package com.example.pokescouttrainer.domain.species

data class PokemonSpecies(
    val id: Int,
    val alias: String,
    val localNames: Map<Language, String>,

    val evolvesFrom: PokemonSpecies? = null,
    val evolvesTo: List<PokemonSpecies> = emptyList(),
) {
    fun name(): String {
        return localNames[Language.ENGLISH]!!
    }
}