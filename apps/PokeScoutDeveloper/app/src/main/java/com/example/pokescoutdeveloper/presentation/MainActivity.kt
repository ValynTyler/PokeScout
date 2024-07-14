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
import com.example.pokescoutdeveloper.presentation.components.MainView
import com.example.pokescoutdeveloper.presentation.theme.PokeScoutDeveloperTheme
import com.example.pokescoutdeveloper.service.NfcService
import java.io.ByteArrayOutputStream
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
                        viewModel.processTextEvent(event)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                writeToTag(it, "Fuck you")
            }
        }
    }

    private fun writeToTag(tag: Tag, data: String) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                it.connect()

                val textRecord = NfcService.createTextRecord(Locale.ENGLISH, data, "name")
                val intRecord = NfcService.createIntRecord(69, "id")

                val message = NdefMessage(arrayOf(textRecord, intRecord))
                it.writeNdefMessage(message)

                it.close()


//                Toast.makeText(this, "Written content: $data", Toast.LENGTH_LONG).show()
//                Log.d("NFC", "Written content: $data")
            } catch (e: Exception) {


//                Toast.makeText(this, "Error writing NFC tag", Toast.LENGTH_LONG).show()
//                Log.e("NFC", "Error writing NFC tag", e)
            }
        }
    }
}
