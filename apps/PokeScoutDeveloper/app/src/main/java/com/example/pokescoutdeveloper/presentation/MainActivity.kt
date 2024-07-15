package com.example.pokescoutdeveloper.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
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
import com.example.pokescoutdeveloper.presentation.components.MainView
import com.example.pokescoutdeveloper.presentation.theme.PokeScoutDeveloperTheme
import com.example.pokescoutdeveloper.service.NfcService
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
                if (!viewModel.state.isReading) {
                    writeToTag(it)
                } else {
                    // Add read logic HERE TODO
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
