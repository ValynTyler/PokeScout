package com.example.pokemonlibrary.domain

import android.nfc.NdefMessage
import com.example.nfc.constant.NfcId
import com.example.nfc.service.NfcWriter

data class PokemonNfcData(
    val trainerName: String = "",
    val speciesId: Int = 0,
    val pokemonXp: Int = 0,
)

fun PokemonNfcData.toNdefMessage(): NdefMessage {
    val nameRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.trainerName,
        NfcId.TRAINER
    )
    val idRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.speciesId.toString(),
        NfcId.SPECIES
    )
    val xpRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.pokemonXp.toString(),
        NfcId.XP
    )

    return NfcWriter.NdefMessageBuilder()
        .addRecord(nameRecord)
        .addRecord(idRecord)
        .addRecord(xpRecord)
        .build()
}