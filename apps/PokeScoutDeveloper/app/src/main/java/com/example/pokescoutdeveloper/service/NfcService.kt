package com.example.pokescoutdeveloper.service

import android.nfc.NdefRecord
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.Locale

object NfcService {
    fun createTextRecord(
        text: String,
        locale: Locale,
    ): NdefRecord {
        val languageBytes = locale.language.toByteArray(Charset.forName("US-ASCII"))
        val textBytes = text.toByteArray(Charsets.UTF_8)
        val payload = ByteArray(1 + languageBytes.size + textBytes.size)
        payload[0] = languageBytes.size.toByte()
        System.arraycopy(languageBytes, 0, payload, 1, languageBytes.size)
        System.arraycopy(textBytes, 0, payload, 1 + languageBytes.size, textBytes.size)
        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload)
    }

    fun createIntRecord(number: Int): NdefRecord {
        val intBytes = ByteBuffer.allocate(4).putInt(number).array()
        return NdefRecord(NdefRecord.TNF_MIME_MEDIA, "application/vnd.com.example.int".toByteArray(), ByteArray(0), intBytes)
    }
}