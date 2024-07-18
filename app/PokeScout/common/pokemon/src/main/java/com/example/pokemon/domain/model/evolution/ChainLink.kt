package com.example.pokemon.domain.model.evolution

import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.result.Result

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
) {
    fun findByIdRecursive(id: Int): Result<ChainLink, Exception> {
        if (this.species.id == id) {
            return Result.Ok(this)
        } else {
            this.evolvesTo.forEach {
                val result = it.findByIdRecursive(id)
                if (result is Result.Ok) {
                    return result
                }
            }
        }
        return Result.Err(Exception("ERROR: Could not find id $id in evolution chain"))
    }
}