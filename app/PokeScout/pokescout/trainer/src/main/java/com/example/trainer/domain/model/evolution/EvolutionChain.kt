package com.example.trainer.domain.model.evolution

import com.example.trainer.domain.model.evolution.ChainLink

data class EvolutionChain(
    val chainId: Int,
    val chainRoot: ChainLink,
) {
    fun maxLen(): Int { // TODO
        var link = this.chainRoot
        var len = 1

        while (link.evolvesTo.isNotEmpty()) {
            link = link.evolvesTo[0]
            len++
        }

        return len
    }
}