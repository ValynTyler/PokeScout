package com.example.pokescoutdeveloper.presentation

data class DeveloperState(
    val inputName: String = "",
    val inputId: Int? = null,
    val inputXp: Int? = null,
    val isWritingNfc: Boolean = false,
)