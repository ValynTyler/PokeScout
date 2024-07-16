package com.example.nfc.error

sealed class NfcWriteError {
    data class FailedToWriteError(val e: Exception): NfcWriteError()
    data object NotNdefCompatibleError: NfcWriteError()
}