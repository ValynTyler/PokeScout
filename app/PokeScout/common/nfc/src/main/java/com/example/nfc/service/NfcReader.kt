package com.example.nfc.service

import android.nfc.NdefMessage
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.example.nfc.error.NfcReadError
import com.example.result.Result

object NfcReader {
    fun readFromTag(tag: Tag): Result<NdefMessage, NfcReadError> {
        val ndef = Ndef.get(tag)
        return if (ndef != null) {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            ndef.close()

            if (ndefMessage != null) {
                Result.Ok(ndefMessage)
            } else {
                Result.Err(NfcReadError.NullNdefMessageError)
            }
        } else {
            Result.Err(NfcReadError.NotNdefFormattedError)
        }
    }
}