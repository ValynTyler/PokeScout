package com.example.trainer.presentation.state

import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.trainer.presentation.state.api.ApiStatus
import com.example.trainer.presentation.state.drawer.DrawerState

sealed class TrainerState {
    data object Closed: TrainerState()
    data class Open(
        val nfcData: PokemonNfcData,
        val apiStatus: ApiStatus,
        val drawer: DrawerState,
    ): TrainerState()
}