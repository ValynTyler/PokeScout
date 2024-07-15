package com.example.pokescoutdeveloper.presentation

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
import com.example.pokescoutdeveloper.domain.PokemonNfcData
import com.example.pokescoutdeveloper.presentation.components.MainView
import com.example.pokescoutdeveloper.presentation.theme.PokeScoutDeveloperTheme
import com.example.pokescoutdeveloper.service.NfcId
import com.example.pokescoutdeveloper.service.NfcRecord
import com.example.pokescoutdeveloper.service.NfcService
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.Locale

class MainActivity : ComponentActivity() {

    // Nfc
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    // Viewmodel
    private val viewModel: DeveloperViewModel by viewModels()

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

        // Visuals
        setContent {
            PokeScoutDeveloperTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    MainView(
                        state = viewModel.state,
                    ) { event ->
                        viewModel.processInputEvent(event)
                    }
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
                if (viewModel.state.isWritingNfc) {
                    writeToTag(it)
                } else {
                    readNfcMessage(it)
                }
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
        var data = PokemonNfcData(
            speciesId = state.inputId ?: 0,
            pokemonXp = state.inputXp ?: 0,
            trainerName = state.inputName,
        )
        when (record) {
            is NfcRecord.TextRecord -> {
                when (record.id) {
                    NfcId.TRAINER.id -> data = data.copy(trainerName = record.value)
                }
            }
            is NfcRecord.IntRecord -> {
                when (record.id) {
                    NfcId.SPECIES.id -> data = data.copy(speciesId = record.value)
                    NfcId.XP.id -> data = data.copy(pokemonXp = record.value)
                }
            }
        }
        viewModel.readNfcData(data)
        Toast.makeText(this, "Successful read", Toast.LENGTH_SHORT).show()
        Log.d("NFC WRITER", "Successful read")
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

                record.tnf == NdefRecord.TNF_MIME_MEDIA && String(record.type).contentEquals("valyntyler.com/pokecamp-master") -> {
                    val payload = record.payload
                    val buffer = ByteBuffer.wrap(payload)
                    val value = buffer.int
                    val id = String(record.id, 0, record.id.size, Charset.forName("UTF-8"))

                    parseNfcRecord(NfcRecord.IntRecord(value, id))
                }
            }
        }
    }

    private fun writeToTag(tag: Tag) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                val state = viewModel.state
                if (
                    state.inputId != null && state.inputId > 0 && state.inputId <= 1025 &&
                    state.inputXp != null && state.inputXp >= 0 && state.inputName != ""
                ) {
                    it.connect()

                    val nameRecord =
                        NfcService.createTextRecord(Locale.ENGLISH, state.inputName, "trainer")
                    val idRecord = NfcService.createIntRecord(state.inputId, "species")
                    val xpRecord = NfcService.createIntRecord(state.inputXp, "xp")

                    val message = NdefMessage(arrayOf(nameRecord, idRecord, xpRecord))
                    it.writeNdefMessage(message)

                    it.close()

                    Toast.makeText(this, "Successful write", Toast.LENGTH_SHORT).show()
                    Log.d("NFC WRITER", "Successful write")
                } else {
                    Toast.makeText(this, "Error: invalid input data", Toast.LENGTH_SHORT).show()
                    Log.d("NFC WRITER", "Error: invalid input data")
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error writing NFC tag", Toast.LENGTH_LONG).show()
                Log.e("NFC WRITER", "Error writing NFC tag", e)
            }
        }
    }
}
