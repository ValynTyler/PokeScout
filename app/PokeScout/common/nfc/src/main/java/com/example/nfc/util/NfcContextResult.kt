package com.example.nfc.util

import com.example.nfc.error.NfcContextError

sealed class NfcContextResult {
    data object Success: NfcContextResult()
    data class Failure(val error: NfcContextError): NfcContextResult()
}