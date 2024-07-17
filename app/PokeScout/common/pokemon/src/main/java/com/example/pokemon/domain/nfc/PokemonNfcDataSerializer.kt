package com.example.pokemon.domain.nfc

import android.util.Log

object PokemonNfcDataSerializer {
    fun BooleanArray.toSerialString(): String {
        var output = ""

        for (i in 0..31) {
            val digit = if (i < this.size) {
                if (this[i]) 1 else 0
            } else {
                0
            }

            output += digit
        }

        return output
    }

    fun String.toDeserializedBooleanArray(): BooleanArray {
        val output = BooleanArray(32)

        for (i in 0..31) {
            output[i] = this[i] == '1'
        }

        return output
    }
}