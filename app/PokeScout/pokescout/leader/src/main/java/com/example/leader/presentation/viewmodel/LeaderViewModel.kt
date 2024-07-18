package com.example.leader.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.leader.presentation.events.InputEvent
import com.example.pokemon.domain.model.GroupType

class LeaderViewModel : ViewModel() {
    var state by mutableStateOf(LeaderState())
        private set

    fun onInputEvent(event: InputEvent) {
        state = when (event) {
            is InputEvent.GroupDropdownSelectionChange -> state.copy(groupDropdownSelection = event.newGroup)
            is InputEvent.TrainerNameChange -> state.copy(trainerNameField = event.newName)
            is InputEvent.PokemonIdChange -> {
                val id = event.newId.replace("\n", "").toIntOrNull()
                if (id == null) {
                    state
                } else if (id > 1025 || id <= 0) {
                    state
                } else {
                    state.copy(pokemonIdField = event.newId)
                }
            }
        }
    }
}