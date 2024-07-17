package com.example.pokemon.domain

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import com.example.nfc.constant.NfcId
import com.example.nfc.service.NfcWriter
import java.nio.charset.Charset
import com.example.result.Result

data class PokemonNfcData(
    val trainerName: String = "",
    val speciesId: Int = 0,
    val evolutionChainId: Int = 0,

    val gymProgress: Array<Boolean> = emptyArray(),
    val dailyPoints: Array<Int> = emptyArray(),
)

fun PokemonNfcData.toNdefMessage(): NdefMessage {
    val trainerRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.trainerName,
        NfcId.TRAINER
    )
    val speciesRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.speciesId .toString(),
        NfcId.SPECIES
    )
    val evolutionChainRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.evolutionChainId.toString(),
        NfcId.EVOLUTION_CHAIN
    )

    return NfcWriter.NdefMessageBuilder()
        .addRecord(trainerRecord)
        .addRecord(speciesRecord)
        .addRecord(evolutionChainRecord)
        .build()
}

fun NdefMessage.toPokemonNfcData(): Result<PokemonNfcData, Exception> {

    var trainer: String? = null
    var species: Int? = null
    var evolutionChain: Int? = null

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
                NfcId.EVOLUTION_CHAIN -> evolutionChain = text.toIntOrNull()
            }
        }
    }

    return if (trainer != null && species != null && evolutionChain != null) {
        Result.Ok(
            PokemonNfcData(
                trainer!!,
                species!!,
                evolutionChain!!,
            )
        )
    } else {
        Result.Err(
            Exception("ERROR: Incomplete NDEF message")
        )
    }
}