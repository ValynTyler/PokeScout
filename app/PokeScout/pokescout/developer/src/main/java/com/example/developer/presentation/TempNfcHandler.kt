package com.example.developer.presentation

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.nfc.service.NfcReader
import com.example.pokemon.domain.toPokemonNfcData
import com.example.result.ok

class NfcHandler {
    var nfcAdapter: NfcAdapter? = null
    lateinit var pendingIntent: PendingIntent
    lateinit var intentFiltersArray: Array<IntentFilter>
    lateinit var techListsArray: Array<Array<String>>
}

fun ComponentActivity.initNfcHandler(nfcHandler: NfcHandler) {
    nfcHandler.nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    if (nfcHandler.nfcAdapter == null) {
        Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_LONG).show()
        finish()
        return
    }
    nfcHandler.pendingIntent = PendingIntent.getActivity(
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
    nfcHandler.intentFiltersArray = arrayOf(ndef)
    nfcHandler.techListsArray = arrayOf(arrayOf(Ndef::class.java.name))
}

fun NfcHandler.onPause(activity: ComponentActivity) {
    nfcAdapter?.disableForegroundDispatch(activity)
}

fun NfcHandler.onResume(activity: ComponentActivity) {
    nfcAdapter?.enableForegroundDispatch(
        activity,
        pendingIntent,
        intentFiltersArray,
        techListsArray
    )
}