package com.example.developer.presentation.input

sealed class InputEvent {
    data object LockEvent : InputEvent()
    data class ListEvent(val index: Int, val checked: Boolean) : InputEvent()
    data class DayEvent(val index: Int, val value: String) : InputEvent()
    sealed class TextEvent(val value : String): InputEvent() {
        class ChangeTrainer(value: String) : TextEvent(value)
        class ChangeSpecies(value: String) : TextEvent(value)
        class ChangeEvolutionChain(value: String) : TextEvent(value)
    }
}