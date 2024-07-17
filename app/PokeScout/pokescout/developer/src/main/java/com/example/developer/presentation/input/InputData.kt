package com.example.developer.presentation.input

import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.result.Result

data class InputData(
    val trainer: String = "",
    val species: Int? = null,
    val evolutionChain: Int? = null,
    val gymBadges: BooleanArray = BooleanArray(32),
    val dailyPoints: Array<Int?> = Array(4) { null },
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
                gymBadges = this.gymBadges,
                dailyPoints = this.dailyPoints.map { it ?: 0 }.toIntArray(),
            )
        )
    } else {
        Result.Err(Exception("ERROR: Invalid input data"))
    }
}