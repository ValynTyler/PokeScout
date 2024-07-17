package com.example.nfc.error

sealed class NfcReadError {
    data object NotNdefFormattedError: NfcReadError()
    data object NullNdefMessageError: NfcReadError()
}