package com.example.pokemon.domain.model.evolution

import com.example.pokemon.domain.model.species.PokemonSpecies

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
) {
    fun findByIdRecursive(speciesId: Int): Result<ChainLink> {
        if (this.species.id == speciesId) {
            return Result.success(this)
        } else {
            this.evolvesTo.forEach {
                val result = it.findByIdRecursive(speciesId)
                if (result.isSuccess) {
                    return result
                }
            }
        }
        return Result.failure(Exception("ID $speciesId not in evolution chain"))
    }
}