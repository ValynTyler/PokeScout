package com.example.pokemon.domain.model.gym

enum class PokemonGym {

    Endurance,
    Domestic,
    Acquaintance,
    Parkour,
    Movie,
    Fleecy,
    Flora,
    Safe,
    NoPoison,
    NonViolent,
    BuildIt,
    PokeCulinaria,
    Stargazer,
    Quest,
    PokeChef;

    override fun toString(): String {
        return when (this) {
            NoPoison -> "No-poison"
            NonViolent -> "Non-violent"
            BuildIt -> "Build-it"
            else -> super.toString()
        }
    }
}