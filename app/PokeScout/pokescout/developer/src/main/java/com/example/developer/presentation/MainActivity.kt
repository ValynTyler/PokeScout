package com.example.developer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfc.NfcHandle
import com.example.nfc.discoverNfcTag
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.toPokemonNfcData
import com.example.result.ok

class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()
    private val nfcHandle = NfcHandle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNfcHandle(nfcHandle)
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
        discoverNfcTag(intent, nfcHandle) {
            NfcReader.readFromTag(it).ok()?.let { value ->
                value.toPokemonNfcData()?.let { data ->
                    viewModel.readNfcData(data)
                }
            }
        }
    }
}