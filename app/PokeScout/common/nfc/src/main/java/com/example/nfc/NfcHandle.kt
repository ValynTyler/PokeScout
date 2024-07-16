package com.example.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast
import androidx.activity.ComponentActivity

class NfcHandle {
    var nfcAdapter: NfcAdapter? = null
    lateinit var pendingIntent: PendingIntent
    lateinit var intentFiltersArray: Array<IntentFilter>
    lateinit var techListsArray: Array<Array<String>>
}

fun ComponentActivity.initNfcHandle(handle: NfcHandle) {
    handle.nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    if (handle.nfcAdapter == null) {
        Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_LONG).show()
        finish()
        return
    }

    handle.pendingIntent = PendingIntent.getActivity(
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

    handle.intentFiltersArray = arrayOf(ndef)
    handle.techListsArray = arrayOf(arrayOf(Ndef::class.java.name))
}

fun ComponentActivity.pauseNfc(handle: NfcHandle) {
    handle.nfcAdapter?.disableForegroundDispatch(this)
}

fun ComponentActivity.resumeNfc(handle: NfcHandle) {
    handle.nfcAdapter?.enableForegroundDispatch(
        this,
        handle.pendingIntent,
        handle.intentFiltersArray,
        handle.techListsArray
    )
}

fun ComponentActivity.discoverNfcTag(intent: Intent, handle: NfcHandle, onTagDiscovered: (Tag) -> Unit) {
    if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let { onTagDiscovered }
    }
}