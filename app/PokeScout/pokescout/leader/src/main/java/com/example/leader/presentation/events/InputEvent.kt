package com.example.leader.presentation.events

import android.text.BoringLayout
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.pokemon.domain.model.GroupType

sealed class InputEvent {
    data object TogglePokeball : InputEvent()
    data class SelectScreen(val screen: LeaderScreenType) : InputEvent()
    sealed class ScreenEvent : InputEvent() {
        sealed class InitScreen : ScreenEvent() {
            data class TrainerNameChange(val newName: String) : InitScreen()
            data class PokemonIdChange(val newId: String) : InitScreen()
            data class GroupDropdownSelectionChange(val newGroup: GroupType) : InitScreen()
        }
        sealed class GymScreen : ScreenEvent() {
            data class GymIndexSelectionChange(val newIndex: Int) : InitScreen()
            data class GroupDropdownSelectionChange(val newGroup: GroupType) : InitScreen()
        }
    }
}