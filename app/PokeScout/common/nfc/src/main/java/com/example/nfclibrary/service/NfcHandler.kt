package com.example.nfclibrary.service

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.nfclibrary.error.NfcContextError
import com.example.nfclibrary.util.NfcContextResult

object NfcHandler {

    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>
    private lateinit var techListsArray: Array<Array<String>>

    fun build(activity: ComponentActivity): NfcContextResult {
        nfcAdapter = NfcAdapter.getDefaultAdapter(activity)
        if (nfcAdapter == null) {
            return NfcContextResult.Failure(NfcContextError.NfcNotAvailableError)
        }

        pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            Intent(activity, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_MUTABLE,
        )

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                return NfcContextResult.Failure(NfcContextError.MalformedMimeTypeError(e))
            }
        }

        intentFiltersArray = arrayOf(ndef)
        techListsArray = arrayOf(arrayOf(Ndef::class.java.name))

        return NfcContextResult.Success
    }

    fun handleContextResult(result: NfcContextResult, activity: ComponentActivity) {
        if (result is NfcContextResult.Failure) {
            when (result.error) {
                is NfcContextError.MalformedMimeTypeError -> {
                    throw RuntimeException("Failed to add MIME type.", result.error.e)
                }

                NfcContextError.NfcNotAvailableError -> {
                    Toast.makeText(
                        activity,
                        "NFC is not available on this device",
                        Toast.LENGTH_LONG
                    ).show()
                    activity.finish()
                }
            }
        }
    }

    fun handlePause(activity: ComponentActivity) {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    fun handleResume(activity: ComponentActivity) {
        nfcAdapter?.enableForegroundDispatch(
            activity,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
    }

    fun handleNewIntent(intent: Intent, onTagDiscovered: (Tag) -> Unit) {
        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                onTagDiscovered
            }
        }
    }
}