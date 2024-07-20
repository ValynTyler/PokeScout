package com.example.leader.presentation.viewmodel

import com.example.option.Option
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.result.Result

sealed class LeaderScreenType {
    data object InitScreen : LeaderScreenType()
    data object SelectScreen : LeaderScreenType()
    data object LoadingScreen : LeaderScreenType()
    data object GymScreen : LeaderScreenType()
    data object ValorScreen : LeaderScreenType()
}