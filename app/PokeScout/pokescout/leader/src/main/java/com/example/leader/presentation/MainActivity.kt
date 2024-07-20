package com.example.leader.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.compose.printError
import com.example.compose.printText
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.viewmodel.LeaderScreenType
import com.example.leader.presentation.viewmodel.LeaderViewModel
import com.example.leader.presentation.viewmodel.toPokemonNfcData
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.nfc.PokemonNfcData
import com.example.pokemon.domain.nfc.toNdefMessage
import com.example.pokemon.domain.nfc.toPokemonNfcData
import com.example.result.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: LeaderViewModel by viewModels()
    private val nfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initNfcHandle(nfcHandle)
        setContent {
            MainView(viewModel.state) { viewModel.onInputEvent(it) }
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
                when (val screen = viewModel.state.activeScreenType) {

                    LeaderScreenType.ScanScreen -> {
                        when (val result = NfcReader.readFromTag(it)) {
                            is Result.Err -> printError("NFC reader", result.error.toString())
                            is Result.Ok -> {
                                when (val dataResult = result.value.toPokemonNfcData()) {
                                    is Result.Err -> printError(
                                        "NFC reader",
                                        dataResult.error.message.toString()
                                    )

                                    is Result.Ok -> {
                                        viewModel.updateNfcData(dataResult.value)
                                        printText("NFC reader", "Data read successfully!")
                                    }
                                }
                            }
                        }
                    }

                    LeaderScreenType.InitScreen -> {
                        if (viewModel.state.isClosed) {
                            when (val nfcDataResult =
                                viewModel.state.infoScreenState.toPokemonNfcData()) {
                                is Result.Err -> printError(
                                    "NFC writer",
                                    nfcDataResult.error.message.toString()
                                )

                                is Result.Ok -> {
                                    NfcWriter.writeToTag(tag, nfcDataResult.value.toNdefMessage())
                                    printText("NFC writer", "Data written successfully!")
                                }
                            }
                            viewModel.onInputEvent(InputEvent.TogglePokeball)
                        }
                    }

                    LeaderScreenType.GymScreen -> {

                    }

                    LeaderScreenType.ValorScreen -> {
                        if (viewModel.state.isClosed) {
                            val nfcDataResult: Result<PokemonNfcData, Exception> =
                                if (viewModel.state.currentNfcData != null)
                                    Result.Ok(viewModel.state.currentNfcData!!)
                                else Result.Err(
                                    Exception("ERROR: Null read tag data")
                                )
                            when (nfcDataResult) {
                                is Result.Err -> printError(
                                    "NFC writer",
                                    nfcDataResult.error.message.toString()
                                )

                                is Result.Ok -> {
                                    NfcWriter.writeToTag(tag, nfcDataResult.value.toNdefMessage())
                                    printText("NFC writer", "Data written successfully!")
                                }
                            }
                            viewModel.clearNfcData()
                            viewModel.onInputEvent(InputEvent.TogglePokeball)
                            viewModel.onInputEvent(InputEvent.SelectScreen(LeaderScreenType.ScanScreen))
                        }
                    }

                    LeaderScreenType.LoadingScreen -> {}
                    LeaderScreenType.SelectScreen -> {}
                }
            }
        }
    }
}