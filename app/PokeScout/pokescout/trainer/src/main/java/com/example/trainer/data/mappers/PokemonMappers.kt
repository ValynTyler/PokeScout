package com.example.trainer.data.mappers

import com.example.trainer.data.remote.LanguageDto
import com.example.trainer.data.remote.LocalizationDto
import com.example.trainer.data.remote.PokemonSpeciesDto
import com.example.trainer.domain.model.Language
import com.example.trainer.domain.model.Localization
import com.example.trainer.domain.model.PokemonSpecies

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