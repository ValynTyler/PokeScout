package com.example.developer.presentation.viewmodel

import com.example.developer.presentation.input.InputData

data class DeveloperState(
    val inputData: InputData = InputData(),
    val isLoadingSpecies: Boolean = false,
    val isLoadingEvolution: Boolean = false,
    val isWritingNfc: Boolean = false,
)