package com.example.nfc.util

import com.example.nfc.error.NfcWriteError

sealed class NfcWriteResult {
    data object Success: NfcWriteResult()
    data class Failure(val error: NfcWriteError): NfcWriteResult()
}