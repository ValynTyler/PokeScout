package com.example.pokescoutdeveloper.presentation

data class DeveloperState(
    val inputName: String = "",
    val inputId: Int? = null,
    val inputXp: Int? = null,
    val isReading: Boolean = false,
)