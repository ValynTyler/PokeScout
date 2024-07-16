package com.example.developer.domain

import android.content.Context
import android.nfc.NdefRecord
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import com.example.developer.presentation.input.toPokemonNfcData
import com.example.developer.presentation.viewmodel.DeveloperState
import com.example.nfc.constant.NfcId
import com.example.nfc.error.NfcReadError
import com.example.nfc.error.NfcWriteError
import com.example.nfc.service.NfcReader
import com.example.nfc.service.NfcWriter
import com.example.nfc.util.NfcReadResult
import com.example.nfc.util.NfcWriteResult
import com.example.pokemonlibrary.domain.PokemonNfcData
import com.example.pokemonlibrary.domain.toNdefMessage
import java.nio.charset.Charset

object PokemonNfcDataParser {
    fun parseTagData(
        tag: Tag,
        state: DeveloperState,
        context: Context,
        onReadPokemonNfcData: (PokemonNfcData) -> Unit
    ) {
        val inputData = state.inputData
        if (state.isWritingNfc) {
            val data = inputData.toPokemonNfcData()
            if (data != null) {
                handleNfcWriteResult(NfcWriter.writeToTag(tag, data.toNdefMessage()), context)
            } else {
                val logSource = "INPUT DATA"
                val errorText = "ERROR: invalid input data"
                Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
                Log.e(logSource, errorText)
            }
        } else {
            handleNfcReadResult(NfcReader.readFromTag(tag), state, context, onReadPokemonNfcData)
        }
    }

    private fun handleNfcWriteResult(result: NfcWriteResult, context: Context) {

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
                Toast.makeText(context, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(context, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleNfcReadResult(
        result: NfcReadResult,
        state: DeveloperState,
        context: Context,
        onReadPokemonNfcData: (PokemonNfcData) -> Unit
    ) {

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

                        parsePokemonData(
                            state,
                            text,
                            id,
                            onReadPokemonNfcData
                        ) // THIS IS WHERE THE PROBLEM IS DIPSHIT TODO
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
                Toast.makeText(context, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(context, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parsePokemonData(
        state: DeveloperState,
        text: String,
        id: String,
        onReadPokemonNfcData: (PokemonNfcData) -> Unit
    ) {
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
    }
}