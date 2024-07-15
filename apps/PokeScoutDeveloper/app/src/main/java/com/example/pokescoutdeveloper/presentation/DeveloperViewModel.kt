package com.example.pokescoutdeveloper.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokescoutdeveloper.domain.PokemonNfcData
import com.example.pokescoutdeveloper.presentation.events.InputEvent

class DeveloperViewModel : ViewModel() {

    var state by mutableStateOf(DeveloperState())
        private set

    fun readNfcData(data: PokemonNfcData) {
        Log.d("FUCK", data.speciesId.toString())
        state = state.copy(
            inputName = data.trainerName,
            inputId = data.speciesId,
            inputXp = data.pokemonXp,
        )
    }

    fun processInputEvent(event: InputEvent) {
        state = when (event) {
            InputEvent.LockEvent -> {
                state.copy(isWritingNfc = !state.isWritingNfc)
            }

            is InputEvent.TextEvent.ChangedName -> {
                state.copy(inputName = event.value.replace("\n", ""))
            }

            is InputEvent.TextEvent.ChangedId -> {
                val value = event.value.replace(" ", "").replace("\n", "").toIntOrNull()
                if (value != null && (value > 1025 || value <= 0)) state else state.copy(inputId = value)
            }

            is InputEvent.TextEvent.ChangedXp -> {
                val value = event.value.replace(" ", "").replace("\n", "").toIntOrNull()
                if (value != null && (value > 100000 || value < 0)) state else state.copy(inputXp = value)
            }
        }
    }

}
