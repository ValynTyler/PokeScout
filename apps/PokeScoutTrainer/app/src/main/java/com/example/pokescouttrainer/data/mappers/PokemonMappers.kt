package com.example.pokescouttrainer.data.mappers

import com.example.pokescouttrainer.data.remote.LanguageDto
import com.example.pokescouttrainer.data.remote.LocalizationDto
import com.example.pokescouttrainer.data.remote.PokemonSpeciesDto
import com.example.pokescouttrainer.domain.species.Language
import com.example.pokescouttrainer.domain.species.Localization
import com.example.pokescouttrainer.domain.species.PokemonSpecies

fun PokemonSpeciesDto.toPokemonSpecies(): PokemonSpecies {
    return PokemonSpecies(
        id = speciesId,
        alias = speciesAlias,
        localNames = speciesLocalNames.map { it.toLocalization() }.associateBy({it.language}, {it.localName})
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