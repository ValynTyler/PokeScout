package com.example.developer.presentation.input

import com.example.pokemon.domain.PokemonNfcData
import com.example.result.Result

data class InputData(
    val trainer: String = "",
    val species: Int? = null,
    val evolutionChain: Int? = null,
)

fun InputData.isValidData(): Boolean {
    return this.trainer != ""
        && this.species != null
        && this.species > 0
        && this.species <= 1025
        && this.evolutionChain != null
        && this.evolutionChain > 0
        && this.evolutionChain <= 549
}

fun InputData.toPokemonNfcData(): Result<PokemonNfcData, Exception> {
    return if (this.isValidData()) {
        Result.Ok(
            PokemonNfcData(
                trainerName = this.trainer,
                speciesId = this.species!!,
                evolutionChainId = this.evolutionChain!!,
            )
        )
    } else {
        Result.Err(Exception("ERROR: Invalid input data"))
    }
}