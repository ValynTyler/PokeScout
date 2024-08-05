package com.example.pokemon.domain.model.evolution

import android.util.Log

data class EvolutionChain(
    val id: Int,
    val rootLink: ChainLink,
) {
    fun findLinkById(speciesId: Int): Result<ChainLink> {
        return rootLink.findByIdRecursive(speciesId).fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(it) },
        )
    }

    fun stage(speciesId: Int): Result<Int> {
        return findLinkById(speciesId).fold(
            onSuccess = {
                var link = it
                var remaining = 0
                while (link.evolvesTo.isNotEmpty()) {
                    link = link.evolvesTo[0]
                    remaining++
                }
                Log.d(remaining.toString(), speciesId.toString())
                Result.success(this.length() - remaining)
            },
//            onFailure = { Result.failure(it) },
            onFailure = { throw(it) },
        )
    }

    fun length(): Int {
        var link = this.rootLink
        var len = 1

        while (link.evolvesTo.isNotEmpty()) {
            link = link.evolvesTo[0]
            len++
        }

        return len
    }
}