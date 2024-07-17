package com.example.trainer.domain.model.evolution

import com.example.result.Result

data class EvolutionChain(
    val chainId: Int,
    val chainRoot: ChainLink,
) {
    fun findLinkById(id: Int): Result<ChainLink, Unit> {
        return chainRoot.findByIdRecursive(id)
    }

    fun maxLen(): Int {
        var link = this.chainRoot
        var len = 1

        while (link.evolvesTo.isNotEmpty()) {
            link = link.evolvesTo[0]
            len++
        }

        return len
    }
}