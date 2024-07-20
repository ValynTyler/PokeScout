package com.example.leader.presentation.viewmodel

import com.example.option.Option
import com.example.pokemon.domain.model.GroupType
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.result.Result

data class LeaderState(
    val isClosed: Boolean = false,
    val activeScreenType: LeaderScreenType = LeaderScreenType.ScanScreen,

    val currentNfcData: PokemonNfcData? = null,

    val infoScreenState: InfoScreenState = InfoScreenState(),
    val gymScreenState: GymScreenState = GymScreenState(),
    val valorScreenState: ValorScreenState = ValorScreenState(),
) {
    data class InfoScreenState(
        val trainerNameField: String = "",
        val pokemonIdField: String = "",
        val groupTypeSelection: GroupType = GroupType.Beginner,

        val currentSpecies: Option<PokemonSpecies> = Option.None(Unit),
        val currentEvolutionChain: Option<EvolutionChain> = Option.None(Unit),
    )

    data class GymScreenState(
        val gymIndexSelection: Int = 0,
    )

    data class ValorScreenState(
        val dayIndexSelection: Int = 0,
    )
}

fun LeaderState.InfoScreenState.toPokemonNfcData(): Result<PokemonNfcData, Exception> {
    if (this.trainerNameField == "") {
        return Result.Err(Exception("Invalid data: No name"))
    }
    if (this.pokemonIdField == "") {
        return Result.Err(Exception("Invalid data: no ID"))
    }

    val speciesId = when (val speciesOption = this.currentSpecies) {
        is Option.None -> return Result.Err(Exception("Invalid data: No species data"))
        is Option.Some -> speciesOption.value.id
    }

    val chainId = when (val chainOption = this.currentEvolutionChain) {
        is Option.None -> return Result.Err(Exception("Invalid data: No evolution chain data"))
        is Option.Some -> chainOption.value.id
    }

    return Result.Ok(
        PokemonNfcData(
            trainerGroup = this.groupTypeSelection.toString(),
            trainerName = this.trainerNameField.trim(),
            speciesId = speciesId,
            evolutionChainId = chainId,
        )
    )
}