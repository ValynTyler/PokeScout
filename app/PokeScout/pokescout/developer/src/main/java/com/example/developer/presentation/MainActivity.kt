package com.example.developer.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.toPokemonNfcData
import com.example.result.ok

class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()
    private val nfcHandler = NfcHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.initNfcHandler(nfcHandler)
        setContent {
            MainView(viewModel.state) {
                viewModel.processInputEvent(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        nfcHandler.onPause(this)
    }

    override fun onResume() {
        super.onResume()
        nfcHandler.onResume(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                NfcReader.readFromTag(it).ok()?.let { value ->
                    value.toPokemonNfcData()?.let { data ->
                        viewModel.readNfcData(data)
                    }
                }
            }
        }
    }
}