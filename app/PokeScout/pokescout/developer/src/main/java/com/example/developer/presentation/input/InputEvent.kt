package com.example.developer.presentation.input

import com.example.pokemon.domain.model.GroupType

sealed class InputEvent {
    data class GroupChanged(val group: GroupType): InputEvent()
    data object LockEvent : InputEvent()
    data class ListEvent(val index: Int, val checked: Boolean) : InputEvent()
    data class DayEvent(val index: Int, val value: String) : InputEvent()
    sealed class TextEvent(val value : String): InputEvent() {
        class ChangeTrainer(value: String) : TextEvent(value)
        class ChangeSpecies(value: String) : TextEvent(value)
        class ChangeEvolutionChain(value: String) : TextEvent(value)
    }
}