package com.example.pokemon.domain.model.evolution

import com.example.result.Result

data class EvolutionChain(
    val id: Int,
    val chainRoot: ChainLink,
) {
    fun findLinkById(id: Int): Result<ChainLink, Exception> {
        return when(val result = chainRoot.findByIdRecursive(id)) {
            is Result.Err -> Result.Err(Exception(result.error.message + " ${this.id}"))
            is Result.Ok -> result
        }
    }

    fun maxLength(): Int {
        var link = this.chainRoot
        var len = 1

        while (link.evolvesTo.isNotEmpty()) {
            link = link.evolvesTo[0]
            len++
        }

        return len
    }
}