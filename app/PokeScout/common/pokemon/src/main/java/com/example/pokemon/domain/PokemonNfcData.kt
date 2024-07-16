package com.example.pokemon.domain

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import com.example.nfc.constant.NfcId
import com.example.nfc.service.NfcWriter
import java.nio.charset.Charset

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

fun NdefMessage.toPokemonNfcData(): PokemonNfcData? {

    var trainer: String? = null
    var species: Int? = null
    var xp: Int? = null

    this.records.forEach { record ->
        if (record.tnf == NdefRecord.TNF_WELL_KNOWN &&
            record.type.contentEquals(NdefRecord.RTD_TEXT)
        ) {
            val id = String(record.id, 0, record.id.size, Charset.forName("UTF-8"))
            val payload = record.payload
            val languageCodeLength = payload[0].toInt() and 0x3F
            val text = String(
                payload,
                languageCodeLength + 1,
                payload.size - languageCodeLength - 1,
                Charset.forName("UTF-8")
            )

            when (id) {
                NfcId.TRAINER -> trainer = text
                NfcId.SPECIES -> species = text.toIntOrNull()
                NfcId.XP -> xp = text.toIntOrNull()
            }
        }
    }

    return if (trainer != null && species != null && xp != null) {
        PokemonNfcData(
            trainer!!,
            xp!!,
            species!!
        )
    } else {
        null
    }
}