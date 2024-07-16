package com.example.nfclibrary.util

import com.example.nfclibrary.error.NfcWriteError

sealed class NfcWriteResult {
    data object Success: NfcWriteResult()
    data class Failure(val error: NfcWriteError): NfcWriteResult()
}