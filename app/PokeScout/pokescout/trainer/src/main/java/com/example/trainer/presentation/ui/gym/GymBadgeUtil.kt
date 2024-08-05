package com.example.trainer.presentation.ui.gym

import com.example.pokemon.domain.model.gym.PokemonGym
import com.example.trainer.R

fun getGymBadgePainterInt(gym: PokemonGym): Int {
    return when (gym) {
        PokemonGym.Endurance -> R.drawable.boulder
        PokemonGym.Domestic -> R.drawable.hive
        PokemonGym.Acquaintance -> R.drawable.soul
        PokemonGym.Parkour -> R.drawable.earth
        PokemonGym.Movie -> R.drawable.fog
        PokemonGym.Fleecy -> R.drawable.cascade
        PokemonGym.Flora -> R.drawable.rainbow
        PokemonGym.Safe -> R.drawable.rising
        PokemonGym.NoPoison -> R.drawable.mineral
        PokemonGym.NonViolent -> R.drawable.storm
        PokemonGym.BuildIt -> R.drawable.plain
        PokemonGym.PokeCulinaria -> R.drawable.volcano
        PokemonGym.Stargazer -> R.drawable.thunder
        PokemonGym.Quest -> R.drawable.marsh
        PokemonGym.PokeChef -> R.drawable.zephyr
    }
}