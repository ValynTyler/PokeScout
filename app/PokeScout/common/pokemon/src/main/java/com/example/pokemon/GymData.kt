package com.example.pokemon

import com.example.pokemon.domain.model.GroupType

val beginnerGymNames = listOf(
    "Domestic GYM",
    "Aquaintance GYM",
    "Parkour GYM",
    "Movie GYM",
    "Fleecy GYM",
    "Flora GYM",
    "No-Poison GYM",
    "Non-violent GYM",
    "Build-it GYM",
    "PokeCulinaria GYM",
    "Stargazer GYM",
    "Quest GYM",
    "PokeChef GYM",
)

val intermediateGymNames = listOf(
    "Endurance GYM",
    "Domestic GYM",
    "Aquaintance GYM",
    "Parkour GYM",
    "Movie GYM",
    "Flora GYM",
    "Safe GYM",
    "No-Poison GYM",
    "Non-violent GYM",
    "Build-it GYM",
    "PokeCulinaria GYM",
    "Stargazer GYM",
    "Quest GYM",
    "PokeChef GYM",
)

val advancedGymNames = listOf(
    "Domestic GYM",
    "Aquaintance GYM",
    "Parkour GYM",
    "Flora GYM",
    "No-Poison GYM",
    "Non-violent GYM",
    "Build-it GYM",
    "PokeCulinaria GYM",
    "Stargazer GYM",
    "Quest GYM",
    "PokeChef GYM",
)

fun gymNames(group: GroupType): List<String> {
    return when (group) {
        GroupType.Beginner -> beginnerGymNames
        GroupType.Intermediate -> intermediateGymNames
        GroupType.Advanced -> advancedGymNames
    }
}