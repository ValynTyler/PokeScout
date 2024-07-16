package com.example.developer.presentation.input

sealed class InputEvent {
    data object LockEvent : InputEvent()
    sealed class TextEvent(val value : String): InputEvent() {
        class ChangedName(value: String) : TextEvent(value)
        class ChangedId(value: String) : TextEvent(value)
        class ChangedXp(value: String) : TextEvent(value)
    }
}