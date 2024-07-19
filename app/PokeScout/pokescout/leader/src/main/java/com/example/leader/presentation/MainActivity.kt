package com.example.leader.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.compose.printError
import com.example.compose.printText
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.ui.PokeballScaffold
import com.example.leader.presentation.ui.PokeballScaffoldPreview
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.leader.presentation.viewmodel.LeaderViewModel
import com.example.leader.presentation.viewmodel.toPokemonNfcData
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.nfc.service.NfcWriter
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
                if (viewModel.state.isWritingNfc) {
                    when (val nfcDataResult = viewModel.state.toPokemonNfcData()) {
                        is Result.Err -> printError(
                            "NFC writer",
                            nfcDataResult.error.message.toString()
                        )

                        is Result.Ok -> {
                            NfcWriter.writeToTag(tag, nfcDataResult.value.toNdefMessage())
                            printText("NFC writer", "Data written successfully!")
                        }
                    }
                    viewModel.onInputEvent(InputEvent.ToggleNfcWriteMode)
                }
            }
        }
    }
}