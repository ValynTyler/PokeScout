package com.example.pokescouttrainer.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pokescouttrainer.domain.nfc.PokemonNfcData
import com.example.pokescouttrainer.presentation.components.PokemonCard
import com.example.pokescouttrainer.presentation.theme.PokeScoutTrainerTheme
import com.example.pokescouttrainer.service.NfcConstants
import com.example.pokescouttrainer.service.NfcId
import com.example.pokescouttrainer.service.NfcRecord
import dagger.hilt.android.AndroidEntryPoint
import java.nio.ByteBuffer
import java.nio.charset.Charset

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Nfc
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    // View model
    private val viewModel: TrainerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Nfc stuff
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE,
        )

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Failed to add MIME type.", e)
            }
        }

        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(arrayOf(Ndef::class.java.name))

        // Load data
        viewModel.updateNfcData(
            PokemonNfcData(
                speciesId = 0,
                trainerName = "-",
                pokemonXp = 0,
            )
        )

        // Visuals
        setContent {
            PokeScoutTrainerTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    PokemonCard(state = viewModel.state)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action
        ) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                readNfcMessage(it)
            }
        }
    }

    private fun readNfcMessage(tag: Tag) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            it.connect()
            val ndefMessage = it.ndefMessage
            parseNdefMessage(ndefMessage)
            it.close()
        }
    }

    private fun <T> parseNfcRecord(record: NfcRecord<T>) {
        val state = viewModel.state
        var data = state.nfcData ?: PokemonNfcData()
        when (record) {
            is NfcRecord.TextRecord -> {
                when (record.id) {
                    NfcId.TRAINER -> data = data.copy(trainerName = record.value)
                }
            }
            is NfcRecord.IntRecord -> {
                when (record.id) {
                    NfcId.SPECIES -> data = data.copy(speciesId = record.value)
                    NfcId.XP -> data = data.copy(pokemonXp = record.value)
                }
            }
        }
        viewModel.updateNfcData(data)
    }

    private fun parseNdefMessage(ndefMessage: NdefMessage?) {
        ndefMessage?.records?.forEach { record ->
            when {
                record.tnf == NdefRecord.TNF_WELL_KNOWN && record.type.contentEquals(NdefRecord.RTD_TEXT) -> {
                    val payload = record.payload
                    val languageCodeLength = payload[0].toInt() and 0x3F
                    val text = String(
                        payload,
                        languageCodeLength + 1,
                        payload.size - languageCodeLength - 1,
                        Charset.forName("UTF-8")
                    )
                    val id = String(record.id, 0, record.id.size, Charset.forName("UTF-8"))

                    parseNfcRecord(NfcRecord.TextRecord(text, id))
                }

                record.tnf == NdefRecord.TNF_MIME_MEDIA && String(record.type).contentEquals(NfcConstants.NFC_TYPE) -> {
                    val payload = record.payload
                    val buffer = ByteBuffer.wrap(payload)
                    val value = buffer.int
                    val id = String(record.id, 0, record.id.size, Charset.forName("UTF-8"))

                    parseNfcRecord(NfcRecord.IntRecord(value, id))
                }
            }
        }
    }
}
