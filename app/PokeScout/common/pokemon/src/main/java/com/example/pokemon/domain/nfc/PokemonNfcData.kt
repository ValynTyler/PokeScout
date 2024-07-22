package com.example.pokemon.domain.nfc

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import com.example.nfc.constant.NfcId
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.model.GroupType
import com.example.pokemon.domain.model.toGroupType
import com.example.pokemon.domain.nfc.PokemonNfcDataSerializer.toDeserializedBooleanArray
import com.example.pokemon.domain.nfc.PokemonNfcDataSerializer.toSerialString
import java.nio.charset.Charset

data class PokemonNfcData(
    val trainerGroup: GroupType,
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

    fun xpPercent(stage: Int, totalStages: Int): Float {
        return (xp() - lastXpThreshold(stage, totalStages)) / (nextXpThreshold(stage, totalStages) - lastXpThreshold(stage, totalStages)).toFloat()
    }

    private fun nextXpThreshold(stage: Int, totalStages: Int): Int {
        return when (totalStages) {
            1 -> 0
            2 -> {
                when (stage) {
                    1 -> 600
                    2 -> 0
                    else -> 0
                }
            }
            3 -> {
                when (stage) {
                    1 -> 400
                    2 -> 600
                    3 -> 0
                    else -> 0
                }
            }
            else -> 0
        }
    }

    private fun lastXpThreshold(stage: Int, totalStages: Int): Int {
        return when (totalStages) {
            1 -> 0
            2 -> {
                when (stage) {
                    1 -> 0
                    2 -> 600
                    else -> 0
                }
            }
            3 -> {
                when (stage) {
                    1 -> 0
                    2 -> 400
                    3 -> 600
                    else -> 0
                }
            }
            else -> 0
        }
    }
}

fun PokemonNfcData.toNdefMessage(): NdefMessage {
    val groupRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
        this.trainerGroup.toString(),
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

fun NdefMessage.toPokemonNfcData(): Result<PokemonNfcData> {

    var group: GroupType = GroupType.Beginner
    var trainer: String? = null
    var species: Int? = null
    var evolutionChain: Int? = null
    var gymBadges = BooleanArray(12)
    val dailyPoints = IntArray(4)

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
                NfcId.GROUP -> group = text.toGroupType()
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
            return Result.failure(Exception("non NDEF_TEXT data detected"))
        }
    }

    return if (trainer != null && species != null && evolutionChain != null) {
        Result.success(
            PokemonNfcData(
                group,
                trainer!!,
                species!!,
                evolutionChain!!,
                gymBadges,
                dailyPoints
            )
        )
    } else {
        Result.failure(
            Exception("Incomplete NDEF message")
        )
    }
}