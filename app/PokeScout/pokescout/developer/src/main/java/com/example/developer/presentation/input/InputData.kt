package com.example.developer.presentation.input

import com.example.pokemon.domain.PokemonNfcData

data class InputData(
    val trainer: String = "",
    val species: Int? = null,
    val xp: Int? = null,
)

fun InputData.isValidData(): Boolean {
    return this.trainer != ""
        && this.species != null
        && this.species > 0
        && this.species <= 1025
        && this.xp != null
        && this.xp >= 0
}

fun InputData.toPokemonNfcData(): PokemonNfcData? {
    return if (this.isValidData()) {
        PokemonNfcData(
            trainerName = this.trainer,
            speciesId = this.species!!,
            pokemonXp = this.xp!!,
        )
    } else {
        null
    }
}