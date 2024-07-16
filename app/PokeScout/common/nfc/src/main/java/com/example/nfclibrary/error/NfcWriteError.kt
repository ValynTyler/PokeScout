package com.example.nfclibrary.error

sealed class NfcWriteError {
    data class FailedToWriteError(val e: Exception): NfcWriteError()
    data object NotNdefCompatibleError: NfcWriteError()
}