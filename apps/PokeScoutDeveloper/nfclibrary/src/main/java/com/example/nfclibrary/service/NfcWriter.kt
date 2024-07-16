package com.example.nfclibrary.service

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import com.example.nfclibrary.error.NfcWriteError
import com.example.nfclibrary.util.NfcWriteResult
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets

object NfcWriter {

    object NdefRecordBuilder {
        fun createTextRecord(text: String, id: String): NdefRecord {

            val textBytes: ByteArray = text.toByteArray(StandardCharsets.UTF_8)
            val languageCodeBytes: ByteArray = "en".toByteArray(StandardCharsets.US_ASCII)
            val idBytes: ByteArray = id.toByteArray()

            // We only have 6 bits to indicate ISO/IANA language code.
            require(languageCodeBytes.size < 64) { "language code is too long, must be <64 bytes." }

            val buffer = ByteBuffer.allocate(1 + languageCodeBytes.size + textBytes.size)
            val status = (languageCodeBytes.size and 0xFF).toByte()

            buffer.put(status)
            buffer.put(languageCodeBytes)
            buffer.put(textBytes)

            return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, idBytes, buffer.array())
        }
    }

    class NdefMessageBuilder(
        private var records: Array<NdefRecord> = emptyArray<NdefRecord>()
    ) {
        fun addRecord(record: NdefRecord): NdefMessageBuilder {
            records += record
            return this
        }

        fun build(): NdefMessage {
            return NdefMessage(records)
        }
    }

    fun writeToTag(tag: Tag, message: NdefMessage): NfcWriteResult {

        val ndef = Ndef.get(tag)
        return if (ndef != null) {
            try {
                ndef.connect()
                ndef.writeNdefMessage(message)
                ndef.close()

                NfcWriteResult.Success
            } catch (e: Exception) {
                NfcWriteResult.Failure(NfcWriteError.FailedToWriteError(e))
            }
        } else {
            NfcWriteResult.Failure(NfcWriteError.NotNdefCompatibleError)
        }
    }
}