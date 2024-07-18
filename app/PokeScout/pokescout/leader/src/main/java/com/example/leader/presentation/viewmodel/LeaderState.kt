package com.example.leader.presentation.viewmodel

import com.example.pokemon.domain.model.GroupType

data class LeaderState(
    val trainerNameField: String = "",
    val pokemonIdField: String = "",
    val groupDropdownSelection: GroupType = GroupType.Beginner,
)