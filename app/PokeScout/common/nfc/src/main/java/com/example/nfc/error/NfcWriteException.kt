package com.example.nfc.error

sealed class NfcWriteException: Exception() {
    data object NotNdefFormattedException: NfcWriteException()
}