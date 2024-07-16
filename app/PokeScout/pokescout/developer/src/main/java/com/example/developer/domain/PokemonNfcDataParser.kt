package com.example.developer.domain

import android.nfc.NdefRecord
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import com.example.developer.presentation.MainActivity
import com.example.developer.presentation.viewmodel.DeveloperState
import com.example.nfclibrary.constant.NfcId
import com.example.nfclibrary.error.NfcReadError
import com.example.nfclibrary.error.NfcWriteError
import com.example.nfclibrary.service.NfcReader
import com.example.nfclibrary.service.NfcWriter
import com.example.nfclibrary.util.NfcReadResult
import com.example.nfclibrary.util.NfcWriteResult
import com.example.pokemonlibrary.domain.PokemonNfcData
import java.nio.charset.Charset

class PokemonNfcDataParser(
    private val activity: MainActivity
) {
    fun parseTagData(tag: Tag, state: DeveloperState, onReadPokemonNfcData: (PokemonNfcData) -> Unit) {
        val inputData = state.inputData
        if (state.isWritingNfc) {
            if (
                inputData.species != null && inputData.species > 0 && inputData.species <= 1025 &&
                inputData.xp != null && inputData.xp >= 0 && inputData.trainer != ""
            ) {
                val nameRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    inputData.trainer,
                    NfcId.TRAINER
                )
                val idRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    inputData.species.toString(),
                    NfcId.SPECIES
                )
                val xpRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    inputData.xp.toString(),
                    NfcId.XP
                )

                val message = NfcWriter.NdefMessageBuilder()
                    .addRecord(nameRecord)
                    .addRecord(idRecord)
                    .addRecord(xpRecord)
                    .build()

                handleNfcWriteResult(NfcWriter.writeToTag(tag, message))

            } else {
                val logSource = "INPUT DATA"
                val errorText = "ERROR: invalid input data"
                Toast.makeText(activity, errorText, Toast.LENGTH_SHORT).show()
                Log.d(logSource, errorText)
            }
        } else {
            handleNfcReadResult(NfcReader.readFromTag(tag), state, onReadPokemonNfcData)
        }
    }

    private fun handleNfcWriteResult(result: NfcWriteResult) {

        val logTag = "NFC WRITER"
        val logMsg: String
        var isError = false

        logMsg = when (result) {
            NfcWriteResult.Success -> "Written successfully"
            is NfcWriteResult.Failure -> {
                isError = true
                when (result.error) {
                    is NfcWriteError.FailedToWriteError -> "Failed to write to NFC tag"
                    NfcWriteError.NotNdefCompatibleError -> "Tag not NDEF compatible"
                }
            }
        }

        when (isError) {
            true -> {
                Log.e(logTag, logMsg)
                Toast.makeText(activity, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(activity, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleNfcReadResult(result: NfcReadResult, state: DeveloperState, onReadPokemonNfcData: (PokemonNfcData) -> Unit) {

        val logTag = "NFC READER"
        val logMsg: String
        var isError = false

        logMsg = when (result) {
            is NfcReadResult.Success -> result.message.let { msg ->

                msg.records.forEach { record ->
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

                        parsePokemonData(state, text, id, onReadPokemonNfcData)
                    }
                }
                // TODO
                "Read successfully"
            }

            is NfcReadResult.Failure -> {
                isError = true
                when (result.error) {
                    NfcReadError.NotNdefFormattedError -> "Tag not NDEF formatted"
                }
            }
        }

        when (isError) {
            true -> {
                Log.e(logTag, logMsg)
                Toast.makeText(activity, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(activity, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parsePokemonData(state: DeveloperState, text: String, id: String, onReadPokemonNfcData: (PokemonNfcData) -> Unit) {
        val inputData = state.inputData
        var data = PokemonNfcData(
            trainerName = inputData.trainer,
            speciesId = inputData.species ?: 0,
            pokemonXp = inputData.xp ?: 0,
        )

        when (id) {
            NfcId.TRAINER -> data = data.copy(trainerName = text)
            NfcId.SPECIES -> data = data.copy(speciesId = text.toInt())
            NfcId.XP -> data = data.copy(pokemonXp = text.toInt())
        }
        onReadPokemonNfcData(data)

        Toast.makeText(activity, "Successful read", Toast.LENGTH_SHORT).show()
        Log.d("NFC WRITER", "Successful read")
    }
}