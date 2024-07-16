package com.example.developer.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfclibrary.service.NfcHandler
import com.example.nfclibrary.service.NfcParser

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
            NfcParser.parseTagData(it)
        }
    }
}
