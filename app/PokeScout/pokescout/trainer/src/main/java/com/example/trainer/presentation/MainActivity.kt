package com.example.trainer.presentation

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.nfc.NfcHandle
import com.example.nfc.initNfcHandle
import com.example.nfc.pauseNfc
import com.example.nfc.resumeNfc
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.PokemonNfcData
import com.example.pokemon.domain.toPokemonNfcData
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

        viewModel.readNfcData(
            PokemonNfcData(
                speciesId = 0,
                trainerName = "-",
                pokemonXp = 0,
            )
        )

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
                NfcReader.readFromTag(it).ok()?.let {value ->
                    value.toPokemonNfcData()?.let { data ->
                        viewModel.readNfcData(data)
                    }
                }
            }
        }
    }
}