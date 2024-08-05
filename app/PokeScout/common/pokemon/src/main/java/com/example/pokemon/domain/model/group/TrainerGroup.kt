package com.example.pokemon.domain.model.group

import com.example.pokemon.domain.model.gym.PokemonGym

object TrainerGroup {
    sealed class Type {
        data object Beginner: Type()
        data object Intermediate: Type()
        data object Advanced: Type()
    }

    fun parseString(string: String): Result<Type> {
        return when (string) {
            "Beginner" -> Result.success(Type.Beginner)
            "Intermediate" -> Result.success(Type.Intermediate)
            "Advanced" -> Result.success(Type.Advanced)
            else -> Result.failure(Exception("Invalid group"))
        }
    }

    fun Type.filterGyms(): List<PokemonGym> {
        val beginnerGyms = listOf(
            PokemonGym.Domestic,
            PokemonGym.Acquaintance,
            PokemonGym.Parkour,
            PokemonGym.Movie,
            PokemonGym.Fleecy,
            PokemonGym.Flora,
            PokemonGym.NoPoison,
            PokemonGym.NonViolent,
            PokemonGym.BuildIt,
            PokemonGym.PokeCulinaria,
            PokemonGym.Stargazer,
            PokemonGym.Quest,
            PokemonGym.PokeChef,
        )

        val intermediateGyms = listOf(
            PokemonGym.Endurance,
            PokemonGym.Domestic,
            PokemonGym.Acquaintance,
            PokemonGym.Parkour,
            PokemonGym.Movie,
            PokemonGym.Flora,
            PokemonGym.Safe,
            PokemonGym.NoPoison,
            PokemonGym.NonViolent,
            PokemonGym.BuildIt,
            PokemonGym.PokeCulinaria,
            PokemonGym.Stargazer,
            PokemonGym.Quest,
            PokemonGym.PokeChef,
        )

        val advancedGyms = listOf(
            PokemonGym.Domestic,
            PokemonGym.Acquaintance,
            PokemonGym.Parkour,
            PokemonGym.Flora,
            PokemonGym.NoPoison,
            PokemonGym.NonViolent,
            PokemonGym.BuildIt,
            PokemonGym.PokeCulinaria,
            PokemonGym.Stargazer,
            PokemonGym.Quest,
            PokemonGym.PokeChef,
        )

        return when(this) {
            TrainerGroup.Type.Beginner -> beginnerGyms
            TrainerGroup.Type.Intermediate -> intermediateGyms
            TrainerGroup.Type.Advanced -> advancedGyms
        }
    }
}