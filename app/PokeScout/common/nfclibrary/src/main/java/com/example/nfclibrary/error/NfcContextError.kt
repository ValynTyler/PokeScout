package com.example.nfclibrary.error

import android.content.IntentFilter

sealed class NfcContextError {
    data object NfcNotAvailableError: NfcContextError()
    data class MalformedMimeTypeError(val e: IntentFilter.MalformedMimeTypeException): NfcContextError()
}