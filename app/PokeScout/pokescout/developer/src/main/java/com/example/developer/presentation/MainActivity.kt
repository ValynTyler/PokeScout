package com.example.developer.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel
import com.example.nfc.service.NfcReader

class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()
    //    private val nfcHandler: NfcHandler = NfcHandler(this)
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        nfcHandler.handleContextResult(nfcHandler.build())
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


        setContent {
            MainView(viewModel.state) {
                viewModel.processInputEvent(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
//        nfcHandler.handlePause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onResume() {
        super.onResume()
//        nfcHandler.handleResume()
        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
//        nfcHandler.handleNewIntent(intent) {
//            PokemonNfcDataParser(this).parseTagData(it, viewModel.state) { data ->
//                viewModel.readNfcData(data)
//            }
//        }
        ///
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
//            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
//            tag?.let {
//                PokemonNfcDataParser.parseTagData(it, viewModel.state, this) { data ->
//                    Log.d("FUCK", data.pokemonXp.toString())
//                    viewModel.readNfcData(data)
//                }
//            }
//        }
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                NfcReader.readFromTag(it)
            }
        }
    }
}
