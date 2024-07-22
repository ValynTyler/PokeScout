package com.example.nfc.service

import android.nfc.NdefMessage
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nfc.error.NfcReadException

object NfcReader {
    @RequiresApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    fun readFromTag(tag: Tag): Result<NdefMessage> {
        val ndef = Ndef.get(tag)
        return if (ndef != null) {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage
            ndef.close()

            if (ndefMessage != null) {
                Result.success(ndefMessage)
            } else {
                Result.failure(NfcReadException.NullNdefMessageException)
            }
        } else {
            Result.failure(NfcReadException.NotNdefFormattedException)
        }
    }
}