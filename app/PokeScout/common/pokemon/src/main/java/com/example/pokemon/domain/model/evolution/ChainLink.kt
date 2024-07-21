package com.example.pokemon.domain.model.evolution

import com.example.pokemon.domain.model.species.PokemonSpecies

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
) {
    fun findByIdRecursive(id: Int): Result<ChainLink> {
        if (this.species.id == id) {
            return Result.success(this)
        } else {
            this.evolvesTo.forEach {
                val result = it.findByIdRecursive(id)
                if (result.isSuccess) {
                    return result
                }
            }
        }
        return Result.failure(Exception("ID $id not in evolution chain"))
    }
}