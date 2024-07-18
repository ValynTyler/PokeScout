package com.example.pokemon.domain.model

sealed class GroupType {
    data object Beginner: GroupType()
    data object Intermediate: GroupType()
    data object Advanced: GroupType()
}