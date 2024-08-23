package com.example.trainer.presentation.state

import android.nfc.Tag
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.nfc.toPokemonNfcData
import com.example.pokemon.domain.repository.PokemonRepository
import com.example.trainer.presentation.state.actions.TrainerAction
import com.example.trainer.presentation.state.api.ApiData
import com.example.trainer.presentation.state.api.ApiStatus
import com.example.trainer.presentation.state.drawer.DrawerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainerViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    var state: TrainerState by mutableStateOf(TrainerState.Closed)
        private set

    fun onAction(action: TrainerAction) {
        when (state) {
            is TrainerState.Closed -> {
                when (action) {
                    is TrainerAction.LoadNfc -> loadNfc(action.tag)
                    else -> {}
                }
            }
            is TrainerState.Open -> {
                when (action) {
                    is TrainerAction.ClearNfc -> clearNfc()

                    is TrainerAction.UpdateDrawerDataMode -> {
                        state = (state as TrainerState.Open).copy(
                           drawer = (state as TrainerState.Open).drawer.copy(
                               dataMode = action.mode
                           )
                        )
                    }

                    is TrainerAction.UpdateDrawerViewMode -> {
                        state = (state as TrainerState.Open).copy(
                            drawer = (state as TrainerState.Open).drawer.copy(
                                viewMode = action.mode
                            )
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    private fun clearNfc() {
        state = TrainerState.Closed
    }

    private fun loadNfc(tag: Tag): Result<Unit> {
        val message = NfcReader.readFromTag(tag)
        message.fold(
            onFailure = { e -> return Result.failure(e) },
            onSuccess = { msg ->
                val data = msg.toPokemonNfcData()
                data.fold(
                    onFailure = { e -> return Result.failure(e)},
                    onSuccess = { nfc ->
                        state = TrainerState.Open(
                            nfcData = nfc,
                            apiStatus = ApiStatus.Loading,
                            drawer = DrawerState(
                                viewMode = DrawerState.ViewMode.GridMode,
                                dataMode = DrawerState.DataMode.GymMode,
                            )
                        )
                        loadApi()
                        return Result.success(Unit)
                    },
                )
            },
        )
    }

    private fun loadApi() {
        viewModelScope.launch {
            when (state) {
                is TrainerState.Closed -> throw(Exception())
                is TrainerState.Open -> state = (state as TrainerState.Open).copy(
                    apiStatus = fetchApi((state as TrainerState.Open).nfcData),
                )
            }
        }
    }

    private suspend fun fetchApi(nfcData: PokemonNfcData): ApiStatus {
        val species = repository.getSpeciesById(nfcData.speciesId).fold(
            onFailure = { return ApiStatus.Error },
            onSuccess = { it }
        )

        val evolution = repository.getEvolutionChainById(species.evolutionChainId).fold(
            onFailure = { return ApiStatus.Error },
            onSuccess = { it }
        )

        return ApiStatus.Success(
            ApiData(
                species,
                evolution
            )
        )
    }
}