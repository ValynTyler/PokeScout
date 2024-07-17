package com.example.developer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.presentation.input.toPokemonNfcData
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.nfc.service.NfcWriter
import com.example.pokemon.domain.toNdefMessage
import com.example.pokemon.domain.toPokemonNfcData
import com.example.result.ok

class MainActivity : ComponentActivity() {

    val viewModel: DeveloperViewModel by viewModels()
    private val nfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initNfcHandle(nfcHandle)
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
                    viewModel.state.inputData.toPokemonNfcData()?.let { data ->
                        NfcWriter.writeToTag(tag, data.toNdefMessage())
                    }
                } else {
                    NfcReader.readFromTag(it).ok()?.let { value ->
                        value.toPokemonNfcData()?.let { data ->
                            viewModel.readNfcData(data)
                        }
                    }
                }
            }
        }
    }
}