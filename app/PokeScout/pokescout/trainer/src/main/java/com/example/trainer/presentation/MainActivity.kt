package com.example.trainer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.nfc.toPokemonNfcData
import com.example.result.Result
import com.example.trainer.presentation.components.MainView
import com.example.trainer.presentation.viewmodel.TrainerViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.result.ok

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TrainerViewModel by viewModels()
    private val nfcHandle: NfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNfcHandle(nfcHandle)
        setContent {
            MainView(viewModel.state)
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

    private fun printText(tag: String, text: String) {
        Log.d(tag, text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun printError(tag: String, error: String) {
        Log.e(tag, error)
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}