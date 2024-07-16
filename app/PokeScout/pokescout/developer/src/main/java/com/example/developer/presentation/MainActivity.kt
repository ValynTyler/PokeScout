package com.example.developer.presentation

import android.content.Intent
import android.nfc.NdefRecord
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.domain.PokemonNfcDataParser
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfclibrary.constant.NfcId
import com.example.nfclibrary.error.NfcReadError
import com.example.nfclibrary.error.NfcWriteError
import com.example.nfclibrary.service.NfcHandler
import com.example.nfclibrary.service.NfcReader
import com.example.nfclibrary.service.NfcWriter
import com.example.nfclibrary.util.NfcReadResult
import com.example.nfclibrary.util.NfcWriteResult
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()
    private val nfcHandler: NfcHandler = NfcHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcHandler.handleContextResult(nfcHandler.build())
        setContent {
            MainView(viewModel.state) {
                viewModel.processInputEvent(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        nfcHandler.handlePause()
    }

    override fun onResume() {
        super.onResume()
        nfcHandler.handleResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        nfcHandler.handleNewIntent(intent) {
            PokemonNfcDataParser(this).parseTagData(it, viewModel.state) { data ->
                viewModel.readNfcData(data)
            }
        }
    }
}
