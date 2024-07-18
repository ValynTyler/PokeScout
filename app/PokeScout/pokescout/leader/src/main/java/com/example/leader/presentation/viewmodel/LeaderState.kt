package com.example.leader.presentation.viewmodel

import com.example.result.Result
import com.example.option.Option
import com.example.pokemon.domain.model.GroupType
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.species.PokemonSpecies
import com.example.pokemon.domain.nfc.PokemonNfcData

data class LeaderState(
    val isWritingNfc: Boolean = false,

    val trainerNameField: String = "",
    val pokemonIdField: String = "",
    val groupDropdownSelection: GroupType = GroupType.Beginner,

    val isLoading: Boolean = false,

    val currentSpecies: Option<PokemonSpecies> = Option.None(Unit),
    val currentEvolutionChain: Option<EvolutionChain> = Option.None(Unit),
)

fun LeaderState.toPokemonNfcData(): Result<PokemonNfcData, Exception> {
    if (this.trainerNameField == "") {
        return Result.Err(Exception("Invalid data: No name"))
    }

    val speciesId = when (val speciesOption = this.currentSpecies) {
        is Option.None -> return Result.Err(Exception("Invalid data: No species data"))
        is Option.Some -> speciesOption.value.id
    }

    val chainId = when (val chainOption = this.currentEvolutionChain) {
        is Option.None -> return Result.Err(Exception("Invalid data: No evolution chain data"))
        is Option.Some -> chainOption.value.id
    }

    return Result.Ok(PokemonNfcData(
        trainerGroup = this.groupDropdownSelection.toString(),
        trainerName = this.trainerNameField,
        speciesId = speciesId,
        evolutionChainId = chainId,
    ))
}