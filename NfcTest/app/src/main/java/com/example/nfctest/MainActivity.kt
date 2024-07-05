package com.example.nfctest

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
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nfctest.ui.theme.NfcTestTheme
import java.io.ByteArrayOutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*

var nfc_text = mutableStateOf("Scan your tag");

class MainActivity : ComponentActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        setContent {
            NfcTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NfcTestUI()
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
                nfc_text.value = readFromTag(it)
                // writeToTag(it, "Hello, NFC!")
            }
        }
    }

    private fun readFromTag(tag: Tag): String {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                it.connect()
                val ndefMessage = it.ndefMessage
                ndefMessage?.let { msg ->
                    for (ndefRecord in msg.records) {
                        if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN &&
                            ndefRecord.type.contentEquals(NdefRecord.RTD_TEXT)) {
                            val payload = String(ndefRecord.payload)
                            Log.d("NFC", "Read content: $payload")
                            Toast.makeText(this, payload, Toast.LENGTH_LONG).show()
                            return payload;
                        }
                    }
                }
                it.close()
            } catch (e: Exception) {
                // Log.e("NFC", "Error reading NFC tag", e)
            }
        }
        return "No NFC info"
    }

    private fun writeToTag(tag: Tag, data: String) {
        val ndef = Ndef.get(tag)
        ndef?.let {
            try {
                it.connect()
                val record = createTextRecord(data, Locale.ENGLISH, true)
                val message = NdefMessage(arrayOf(record))
                it.writeNdefMessage(message)
                it.close()
                Log.d("NFC", "Written content: $data")
            } catch (e: Exception) {
                Log.e("NFC", "Error writing NFC tag", e)
            }
        }
    }

    private fun createTextRecord(payload: String, locale: Locale, encodeInUtf8: Boolean): NdefRecord {
        val langBytes = locale.language.toByteArray(Charset.forName("US-ASCII"))
        val utfEncoding = if (encodeInUtf8) Charset.forName("UTF-8") else Charset.forName("UTF-16")
        val textBytes = payload.toByteArray(utfEncoding)
        val utfBit = if (encodeInUtf8) 0 else 1 shl 7
        val status = (utfBit + langBytes.size).toChar()
        val baos = ByteArrayOutputStream(1 + langBytes.size + textBytes.size)
        baos.write(status.toInt())
        baos.write(langBytes, 0, langBytes.size)
        baos.write(textBytes, 0, textBytes.size)
        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), baos.toByteArray())
    }
}

@Composable
fun NfcTestUI() {
    val text by nfc_text;

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NfcTestTheme {
        NfcTestUI()
    }
}
