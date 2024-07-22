package com.example.nfc.error

sealed class NfcReadException: Exception() {
    data object NotNdefFormattedException: NfcReadException()
    data object NullNdefMessageException: NfcReadException()
}