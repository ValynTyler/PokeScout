package com.example.pokescoutdeveloper.presentation.events

sealed class TextEvent(
    val value: String
) {
    class ChangedName(value: String) : TextEvent(value)
    class ChangedId(value: String) : TextEvent(value)
    class ChangedXp(value: String) : TextEvent(value)
}