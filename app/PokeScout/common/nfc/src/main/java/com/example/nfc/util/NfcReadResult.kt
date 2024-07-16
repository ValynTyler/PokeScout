package com.example.nfc.util

import android.nfc.NdefMessage
import com.example.nfc.error.NfcReadError

sealed class NfcReadResult {
    data class Success(val message: NdefMessage): NfcReadResult()
    data class Failure(val error: NfcReadError): NfcReadResult()
}