package com.example.pokemon.domain.model.gym

import com.example.pokemon.domain.model.group.TrainerGroup

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

    fun filterByGroup(type: TrainerGroup.Type): List<PokemonGym> {
        val beginnerGyms = listOf(
            Domestic,
            Acquaintance,
            Parkour,
            Movie,
            Fleecy,
            Flora,
            NoPoison,
            NonViolent,
            BuildIt,
            PokeCulinaria,
            Stargazer,
            Quest,
            PokeChef,
        )

        val intermediateGyms = listOf(
            Endurance,
            Domestic,
            Acquaintance,
            Parkour,
            Movie,
            Flora,
            Safe,
            NoPoison,
            NonViolent,
            BuildIt,
            PokeCulinaria,
            Stargazer,
            Quest,
            PokeChef,
        )

        val advancedGyms = listOf(
            Domestic,
            Acquaintance,
            Parkour,
            Flora,
            NoPoison,
            NonViolent,
            BuildIt,
            PokeCulinaria,
            Stargazer,
            Quest,
            PokeChef,
        )

        return when(type) {
            TrainerGroup.Type.Beginner -> beginnerGyms
            TrainerGroup.Type.Intermediate -> intermediateGyms
            TrainerGroup.Type.Advanced -> advancedGyms
        }
    }
}