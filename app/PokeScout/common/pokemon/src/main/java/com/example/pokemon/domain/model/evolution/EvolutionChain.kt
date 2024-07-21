package com.example.pokemon.domain.model.evolution

data class EvolutionChain(
    val id: Int,
    val rootLink: ChainLink,
) {
    fun findLinkById(id: Int): Result<ChainLink> {
        return rootLink.findByIdRecursive(id).fold(
            onSuccess = { Result.success(it) },
            onFailure = { Result.failure(it) },
        )
    }

    fun stage(id: Int): Result<Int> {
        return findLinkById(id).fold(
            onSuccess = {
                var link = this.rootLink
                var remaining = 0
                while (link.evolvesTo.isNotEmpty()) {
                    link = link.evolvesTo[0]
                    remaining++
                }
                Result.success(this.length() - remaining)
            },
            onFailure = {Result.failure(it)},
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