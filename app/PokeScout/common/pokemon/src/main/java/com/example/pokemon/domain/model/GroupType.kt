package com.example.pokemon.domain.model

sealed class GroupType {
    data object Beginner: GroupType()
    data object Intermediate: GroupType()
    data object Advanced: GroupType()
}

fun String.toGroupType(): GroupType {
    return when (this) {
        "Beginner" -> GroupType.Beginner
        "Intermediate" -> GroupType.Intermediate
        "Advanced" -> GroupType.Advanced
        else -> throw Exception("Invalid group")
    }
}