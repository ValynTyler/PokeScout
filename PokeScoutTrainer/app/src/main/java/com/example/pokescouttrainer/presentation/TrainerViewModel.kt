package com.example.pokescouttrainer.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import javax.inject.Inject

@HiltViewModel
class TrainerViewModel @Inject constructor(
//    private val repository: PokeApiClient = PokeApiClient(),
) : ViewModel() {

    private val repository: PokeApiClient = PokeApiClient()

    var state by mutableStateOf(TrainerState())
        private set

    fun loadSpeciesData(id: Int) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
            )

            state = state.copy(
//                speciesData = ,
            )

            state = state.copy(
                isLoading = false,
            )
        }
    }
}