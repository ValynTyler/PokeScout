package com.example.trainer.presentation.state.actions

import android.nfc.Tag
import com.example.trainer.presentation.state.api.ApiStatus
import com.example.trainer.presentation.state.drawer.DrawerState

sealed class TrainerAction {
    // nfc
    data class LoadNfc(val tag: Tag): TrainerAction()
    data object ClearNfc: TrainerAction()

    // drawer
    data class UpdateDrawerViewMode(val mode: DrawerState.ViewMode): TrainerAction()
    data class UpdateDrawerDataMode(val mode: DrawerState.DataMode): TrainerAction()
}