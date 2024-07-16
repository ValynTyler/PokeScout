package com.example.nfc.service

import android.nfc.Tag
import android.nfc.tech.Ndef
import com.example.nfc.error.NfcReadError
import com.example.nfc.util.NfcReadResult

object NfcReader {
    fun readFromTag(tag: Tag): NfcReadResult {
        val ndef = Ndef.get(tag)
        return if (ndef != null) {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            ndef.close()

            NfcReadResult.Success(ndefMessage)
        } else {
            NfcReadResult.Failure(NfcReadError.NotNdefFormattedError)
        }
    }
}