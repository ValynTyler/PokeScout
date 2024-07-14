package com.example.pokescoutdeveloper.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokescoutdeveloper.presentation.events.TextEvent

class DeveloperViewModel : ViewModel() {

    var state by mutableStateOf(DeveloperState())
        private set

    fun processTextEvent(event: TextEvent) {
        state = when (event) {
            is TextEvent.ChangedName -> {
                state.copy(inputName = event.value.replace("\n", ""))
            }

            is TextEvent.ChangedId -> {
                val value = event.value.replace(" ", "").replace("\n", "").toIntOrNull()
                if (value != null && (value > 1025 || value < 0)) state else state.copy(inputId = value)
            }

            is TextEvent.ChangedXp -> {
                val value = event.value.replace(" ", "").replace("\n", "").toIntOrNull()
                if (value != null && (value > 100000 || value <= 0)) state else state.copy(inputXp = value)
            }
        }
    }

}
