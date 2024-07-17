package com.example.pokemon.data.mappers

import com.example.pokemon.data.remote.PokemonApi
import com.example.pokemon.data.remote.evolution.ChainLinkDto
import com.example.pokemon.data.remote.evolution.EvolutionChainDto
import com.example.pokemon.data.remote.locale.LanguageDto
import com.example.pokemon.data.remote.locale.LocalizationDto
import com.example.pokemon.data.remote.species.SpeciesDto
import com.example.pokemon.domain.model.evolution.ChainLink
import com.example.pokemon.domain.model.evolution.EvolutionChain
import com.example.pokemon.domain.model.locale.Language
import com.example.pokemon.domain.model.locale.Localization
import com.example.pokemon.domain.model.species.PokemonSpecies

fun SpeciesDto.toPokemonSpecies(): PokemonSpecies {
    return PokemonSpecies(
        id = id,
        alias = alias,
        localNames = localNames.map { it.toLocalization() }
            .associateBy({ it.language }, { it.localName }),
        evolvesFromId = evolvesFromSpeciesUrl
            ?.url
            ?.removePrefix("https://pokeapi.co/api/v2/pokemon-species/")
            ?.removeSuffix("/")
            ?.toIntOrNull(),
        evolutionChainId = evolutionChainUrl.url
            .removePrefix("https://pokeapi.co/api/v2/evolution-chain/")
            .removeSuffix("/")
            .toInt()
    )
}

suspend fun EvolutionChainDto.toChainLink(api: PokemonApi): EvolutionChain {
    val link = this.rootLink
    return EvolutionChain(
        this.evolutionChainId,
        link.toChainLink(api)
    )
}

suspend fun ChainLinkDto.toChainLink(api: PokemonApi): ChainLink {
    val id = this.speciesUrl.url
        .removePrefix("https://pokeapi.co/api/v2/pokemon-species/")
        .removeSuffix("/")
        .toInt()
    val species = api.getSpeciesById(id)

    return ChainLink(
        species = species.toPokemonSpecies(),
        evolvesTo = evolvesTo.map {
            it.toChainLink(api)
        }
    )
}

fun LocalizationDto.toLocalization(): Localization {
    return Localization(
        language = language.toLanguage(),
        localName = localName,
    )
}

fun LanguageDto.toLanguage(): Language {
    return when (languageName) {
        "ko" -> Language.KOREAN
        "fr" -> Language.FRENCH
        "de" -> Language.GERMAN
        "es" -> Language.SPANISH
        "it" -> Language.ITALIAN
        "en" -> Language.ENGLISH
        "ja" -> Language.JAPANESE
        else -> Language.UNKNOWN
    }
}