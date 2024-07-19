package com.example.trainer.presentation.viewmodel

import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.result.Result

data class TrainerState(
    val isOpen: Boolean = true,

    val nfcData: PokemonNfcData? = null,

    val ancestorData: PokemonSpecies? = null,
    val speciesData: PokemonSpecies? = null,
    val evolutionData: EvolutionChain? = null,

    val isLoading: Boolean = false,
) {
    fun currentEvolutionStage(): Int? {
        if (evolutionData == null || nfcData == null) {
            return null
        }

        return when (val linkResult = evolutionData.findLinkById(nfcData.speciesId)) {
            is Result.Err -> null
            is Result.Ok -> {
                var cnt = 0
                var link = linkResult.value

                while (link.evolvesTo.isNotEmpty()) {
                    cnt++
                    link = link.evolvesTo[0]
                }

                evolutionData.maxLength() - cnt
            }
        }
    }
}