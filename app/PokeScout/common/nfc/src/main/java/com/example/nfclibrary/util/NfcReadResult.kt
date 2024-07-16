package com.example.nfclibrary.util

import android.nfc.NdefMessage
import com.example.nfclibrary.error.NfcReadError

sealed class NfcReadResult {
    data class Success(val message: NdefMessage): NfcReadResult()
    data class Failure(val error: NfcReadError): NfcReadResult()
}