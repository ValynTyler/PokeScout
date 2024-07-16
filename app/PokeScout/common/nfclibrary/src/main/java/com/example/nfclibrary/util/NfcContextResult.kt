package com.example.nfclibrary.util

import com.example.nfclibrary.error.NfcContextError

sealed class NfcContextResult {
    data object Success: NfcContextResult()
    data class Failure(val error: NfcContextError): NfcContextResult()
}