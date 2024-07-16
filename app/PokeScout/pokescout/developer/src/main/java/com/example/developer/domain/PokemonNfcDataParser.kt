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
import com.example.nfc.service.NfcWriter
import com.example.nfc.util.NfcReadResult
import com.example.nfc.util.NfcWriteResult
import com.example.pokemon.domain.PokemonNfcData
import com.example.pokemon.domain.toNdefMessage
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
//            handleNfcReadResult(NfcReader.readFromTag(tag), state, context, onReadPokemonNfcData)
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
}