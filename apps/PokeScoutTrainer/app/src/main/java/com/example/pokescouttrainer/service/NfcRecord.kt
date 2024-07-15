package com.example.pokescouttrainer.service

sealed class NfcRecord<T>(
    val value: T,
    val id: String,
) {
    class TextRecord(value: String, id: String): NfcRecord<String>(value, id)
    class IntRecord(value: Int, id: String): NfcRecord<Int>(value, id)
}