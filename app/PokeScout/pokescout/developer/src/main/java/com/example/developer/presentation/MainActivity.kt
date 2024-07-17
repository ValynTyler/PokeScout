package com.example.developer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.compose.printError
import com.example.compose.printText
import com.example.developer.presentation.input.toPokemonNfcData
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.nfc.PokemonNfcDataSerializer.toSerialString
import com.example.pokemon.domain.nfc.toNdefMessage
import com.example.pokemon.domain.nfc.toPokemonNfcData
import com.example.result.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()
    private val nfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initNfcHandle(nfcHandle)

        Log.d("",
            viewModel.state.inputData.gymBadges[6].toString())

        setContent {
            MainView(viewModel.state) {
                viewModel.processInputEvent(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pauseNfc(nfcHandle)
    }

    override fun onResume() {
        super.onResume()
        resumeNfc(nfcHandle)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                if (viewModel.state.isWritingNfc) {
                    when (val nfcDataResult = viewModel.state.inputData.toPokemonNfcData()) {
                        is Result.Err -> printError("NFC writer", nfcDataResult.error.message.toString())
                        is Result.Ok -> {
                            NfcWriter.writeToTag(tag, nfcDataResult.value.toNdefMessage())
                            printText("NFC writer", "Data written successfully!")
                        }
                    }
                } else {
                    when (val result = NfcReader.readFromTag(it)) {
                        is Result.Err -> printError("NFC reader", result.error.toString())
                        is Result.Ok -> {
                            when (val dataResult = result.value.toPokemonNfcData()) {
                                is Result.Err -> printError(
                                    "NFC reader",
                                    dataResult.error.message.toString()
                                )

                                is Result.Ok -> {
                                    viewModel.readNfcData(dataResult.value)
                                    printText("NFC reader", "Data read successfully!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
