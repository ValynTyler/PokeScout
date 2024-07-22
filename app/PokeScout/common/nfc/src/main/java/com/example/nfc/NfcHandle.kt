package com.example.nfc

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi

class NfcHandle {
    var nfcAdapter: NfcAdapter? = null
    lateinit var pendingIntent: PendingIntent
    lateinit var intentFiltersArray: Array<IntentFilter>
    lateinit var techListsArray: Array<Array<String>>
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD_MR1)
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

@RequiresApi(Build.VERSION_CODES.GINGERBREAD_MR1)
fun ComponentActivity.pauseNfc(handle: NfcHandle) {
    handle.nfcAdapter?.disableForegroundDispatch(this)
}

@RequiresApi(Build.VERSION_CODES.GINGERBREAD_MR1)
fun ComponentActivity.resumeNfc(handle: NfcHandle) {
    handle.nfcAdapter?.enableForegroundDispatch(
        this,
        handle.pendingIntent,
        handle.intentFiltersArray,
        handle.techListsArray
    )
}