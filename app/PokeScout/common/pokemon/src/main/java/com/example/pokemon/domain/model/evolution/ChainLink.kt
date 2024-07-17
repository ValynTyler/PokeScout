package com.example.pokemon.domain.model.evolution

import com.example.result.Result
import com.example.pokemon.domain.model.species.PokemonSpecies

data class ChainLink(
    val species: PokemonSpecies,
    val evolvesTo: List<ChainLink> = emptyList(),
) {
    fun findByIdRecursive(id: Int): Result<ChainLink, Unit> {
        if (this.species.id == id) {
            return Result.Ok(this)
        } else {
            this.evolvesTo.forEach {
                return it.findByIdRecursive(id)
            }
        }
        return Result.Err(Unit)
    }
}