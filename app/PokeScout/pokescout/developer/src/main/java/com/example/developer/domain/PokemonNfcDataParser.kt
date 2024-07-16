package com.example.developer.domain

import android.content.Context
import android.nfc.Tag
import com.example.developer.presentation.input.toPokemonNfcData
import com.example.developer.presentation.viewmodel.DeveloperState
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.toNdefMessage

object PokemonNfcDataParser {
    fun parseTagData(
        tag: Tag,
        state: DeveloperState,
        context: Context,
    ) {
        state.inputData.toPokemonNfcData()?.let {
            NfcWriter.writeToTag(tag, it.toNdefMessage())
        }
    }
}