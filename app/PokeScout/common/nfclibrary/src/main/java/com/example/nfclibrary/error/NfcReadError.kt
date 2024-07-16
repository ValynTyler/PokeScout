package com.example.nfclibrary.error

sealed class NfcReadError {
    data object NotNdefFormattedError: NfcReadError()
}