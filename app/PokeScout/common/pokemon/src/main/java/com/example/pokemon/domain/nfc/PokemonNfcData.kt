package com.example.pokemon.domain.nfc

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import com.example.nfc.constant.NfcId
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.nfc.PokemonNfcDataSerializer.toDeserializedBooleanArray
import com.example.pokemon.domain.nfc.PokemonNfcDataSerializer.toSerialString
import com.example.result.Result
import java.nio.charset.Charset

data class PokemonNfcData(
    val trainerGroup: String = "",
    val trainerName: String = "",
    val speciesId: Int = 0,
    val evolutionChainId: Int = 0,

    val gymBadges: BooleanArray = BooleanArray(12),
    val dailyPoints: IntArray = IntArray(4),
) {
    fun xp(): Int {
        var xp = 0
        this.dailyPoints.forEach { xp += 15 * it }
        this.gymBadges.forEach { if (it) xp += 50 }

        return xp
    }
}

fun PokemonNfcData.toNdefMessage(): NdefMessage {
    val groupRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.trainerGroup,
        NfcId.GROUP
    )

    val trainerRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.trainerName,
        NfcId.TRAINER
    )

    val speciesRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.speciesId.toString(),
        NfcId.SPECIES
    )

    val evolutionChainRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.evolutionChainId.toString(),
        NfcId.EVOLUTION_CHAIN
    )

    val gymProgressRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.gymBadges.toSerialString(),
        NfcId.GYM_PROGRESS
    )

    val day1Points = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.dailyPoints[0].toString(),
        NfcId.DAY_1_POINTS
    )

    val day2Points = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.dailyPoints[1].toString(),
        NfcId.DAY_2_POINTS
    )

    val day3Points = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.dailyPoints[2].toString(),
        NfcId.DAY_3_POINTS
    )

    val day4Points = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.dailyPoints[3].toString(),
        NfcId.DAY_4_POINTS
    )

    return NfcWriter.NdefMessageBuilder()
        .addRecord(groupRecord)
        .addRecord(trainerRecord)
        .addRecord(speciesRecord)
        .addRecord(evolutionChainRecord)
        .addRecord(gymProgressRecord)
        .addRecord(day1Points)
        .addRecord(day2Points)
        .addRecord(day3Points)
        .addRecord(day4Points)
        .build()
}

fun NdefMessage.toPokemonNfcData(): Result<PokemonNfcData, Exception> {

    var group: String? = null
    var trainer: String? = null
    var species: Int? = null
    var evolutionChain: Int? = null
    var gymBadges = BooleanArray(12)
    var dailyPoints: IntArray = IntArray(4)

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
                NfcId.GROUP -> group = text
                NfcId.TRAINER -> trainer = text
                NfcId.SPECIES -> species = text.toIntOrNull()
                NfcId.EVOLUTION_CHAIN -> evolutionChain = text.toIntOrNull()
                NfcId.GYM_PROGRESS -> gymBadges = text.toDeserializedBooleanArray()
                NfcId.DAY_1_POINTS -> dailyPoints[0] = text.toInt().or(0)
                NfcId.DAY_2_POINTS -> dailyPoints[1] = text.toInt().or(0)
                NfcId.DAY_3_POINTS -> dailyPoints[2] = text.toInt().or(0)
                NfcId.DAY_4_POINTS -> dailyPoints[3] = text.toInt().or(0)
            }
        } else {
            return Result.Err(Exception("ERROR: non NDEF_TEXT data detected"))
        }
    }

    return if (group != null && trainer != null && species != null && evolutionChain != null) {
        Result.Ok(
            PokemonNfcData(
                group!!,
                trainer!!,
                species!!,
                evolutionChain!!,
                gymBadges,
                dailyPoints
            )
        )
    } else {
        Result.Err(
            Exception("ERROR: Incomplete NDEF message")
        )
    }
}