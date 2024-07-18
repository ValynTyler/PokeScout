package com.example.leader.presentation.events

import com.example.pokemon.domain.model.GroupType

sealed class InputEvent {
    data class TrainerNameChange(val newName: String): InputEvent()
    data class PokemonIdChange(val newId: String): InputEvent()
    data class GroupDropdownSelectionChange(val newGroup: GroupType): InputEvent()
}