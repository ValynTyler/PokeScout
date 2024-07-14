package com.example.pokescouttrainer.domain.nfc

data class PokemonNfcData(
    val speciesId: Int = 0,
    val pokemonXp: Int = 0,
    val pokemonNick: String = "",
    val trainerName: String = "",
)
