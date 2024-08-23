package com.example.trainer.presentation.state.drawer

data class DrawerState(
    val dataMode: DataMode,
    val viewMode: ViewMode,
) {

    sealed class DataMode {
        data object DayMode: DataMode()
        data object GymMode: DataMode()
    }

    sealed class ViewMode {
        data object GridMode: ViewMode()
        data object ListMode: ViewMode()
    }
}