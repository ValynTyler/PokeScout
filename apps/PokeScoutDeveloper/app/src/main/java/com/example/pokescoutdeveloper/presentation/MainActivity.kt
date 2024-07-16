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
import com.example.nfclibrary.constant.NfcId
import com.example.nfclibrary.error.NfcReadError
import com.example.nfclibrary.error.NfcWriteError
import com.example.nfclibrary.service.NfcReader
import com.example.nfclibrary.service.NfcReader.readFromTag
import com.example.nfclibrary.service.NfcWriter
import com.example.nfclibrary.service.NfcWriter.writeToTag
import com.example.nfclibrary.util.NfcReadResult
import com.example.nfclibrary.util.NfcWriteResult
import com.example.pokescoutdeveloper.domain.PokemonNfcData
import com.example.pokescoutdeveloper.presentation.components.MainView
import com.example.pokescoutdeveloper.presentation.theme.PokeScoutDeveloperTheme
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    // Nfc
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    // Viewmodel
    private val viewModel: DeveloperViewModel by viewModels()

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                handleNfcTagContents(it, viewModel.state)
            }
        }
    }

    private fun handleNfcTagContents(tag: Tag, state: DeveloperState) {
        if (viewModel.state.isWritingNfc) {
            if (
                state.inputId != null && state.inputId > 0 && state.inputId <= 1025 &&
                state.inputXp != null && state.inputXp >= 0 && state.inputName != ""
            ) {
                val nameRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    state.inputName,
                    NfcId.TRAINER
                )
                val idRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    state.inputId.toString(),
                    NfcId.SPECIES
                )
                val xpRecord = NfcWriter.NdefRecordBuilder.createTextRecord(
                    state.inputXp.toString(),
                    NfcId.XP
                )

                val message = NfcWriter.NdefMessageBuilder()
                    .addRecord(nameRecord)
                    .addRecord(idRecord)
                    .addRecord(xpRecord)
                    .build()

                handleNfcWriteResult(writeToTag(tag, message))

            } else {
                val logSource = "INPUT DATA"
                val errorText = "ERROR: invalid input data"
                Toast.makeText(this, errorText, Toast.LENGTH_SHORT).show()
                Log.d(logSource, errorText)
            }
        } else {
            handleNfcReadResult(readFromTag(tag))
        }
    }

    private fun handleNfcWriteResult(result: NfcWriteResult) {

        val logTag = "NFC WRITER"
        val logMsg: String
        var isError = false

        logMsg = when (result) {
            NfcWriteResult.Success -> "Written successfully"
            is NfcWriteResult.Failure -> {
                isError = true
                when (result.error) {
                    is NfcWriteError.FailedToWriteError -> "Failed to write to NFC tag"
                    NfcWriteError.NotNdefCompatibleError -> "Tag not NDEF compatible"
                }
            }
        }

        when (isError) {
            true -> {
                Log.e(logTag, logMsg)
                Toast.makeText(this, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(this, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleNfcReadResult(result: NfcReadResult) {

        val logTag = "NFC READER"
        val logMsg: String
        var isError = false

        logMsg = when (result) {
            is NfcReadResult.Success -> result.message.let { msg ->

                msg.records.forEach { record ->
                    if (record.tnf == NdefRecord.TNF_WELL_KNOWN &&
                        record.type.contentEquals(NdefRecord.RTD_TEXT)
                    ) {
                        val id = String(record.id, 0, record.id.size, Charset.forName("UTF-8"))
                        val payload = record.payload
                        val languageCodeLength = payload[0].toInt() and 0x3F
                        val text = String(
                            payload,
                            languageCodeLength + 1,
                            payload.size - languageCodeLength - 1,
                            Charset.forName("UTF-8")
                        )

                        parseNfcData(text, id)
                    }
                }
                // TODO
                "Read successfully"
            }

            is NfcReadResult.Failure -> {
                isError = true
                when (result.error) {
                    NfcReadError.NotNdefFormattedError -> "Tag not NDEF formatted"
                }
            }
        }

        when (isError) {
            true -> {
                Log.e(logTag, logMsg)
                Toast.makeText(this, logMsg, Toast.LENGTH_LONG).show()
            }

            false -> {
                Log.d(logTag, logMsg)
                Toast.makeText(this, logMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseNfcData(text: String, id: String) {
        val state = viewModel.state
        var data = PokemonNfcData(
            speciesId = state.inputId ?: 0,
            pokemonXp = state.inputXp ?: 0,
            trainerName = state.inputName,
        )

        when (id) {
            NfcId.TRAINER -> data = data.copy(trainerName = text)
            NfcId.SPECIES -> data = data.copy(speciesId = text.toInt())
            NfcId.XP -> data = data.copy(pokemonXp = text.toInt())
        }
        viewModel.readNfcData(data)

        Toast.makeText(this, "Successful read", Toast.LENGTH_SHORT).show()
        Log.d("NFC WRITER", "Successful read")
    }
}
