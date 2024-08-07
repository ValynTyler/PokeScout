package com.example.trainer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.compose.printError
import com.example.compose.printText
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.nfc.toPokemonNfcData
import com.example.trainer.presentation.state.Trainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: Trainer.ViewModel by viewModels()
    private val nfcHandle: NfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNfcHandle(nfcHandle)
        setContent {
            MainView(viewModel.state) { viewModel.onEvent(it) }
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
                val message = NfcReader.readFromTag(it)
                message.fold(
                    onFailure = { e -> printError("NFC reader", e.message.toString()) },
                    onSuccess = { msg ->
                        val data = msg.toPokemonNfcData()
                        data.fold(
                            onFailure = { e -> printError("NFC reader", e.message.toString()) },
                            onSuccess = { nfcData ->
                                printText("NFC reader", "Data read successfully!")
                                viewModel.populate(nfcData = nfcData)
                            },
                        )
                    },
                )
            }
        }
    }
}