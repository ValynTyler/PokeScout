package com.example.compose

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity

fun Context.printText(tag: String, text: String) {
    Log.d(tag, text)
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.printError(tag: String, error: String) {
    Log.e(tag, error)
    Toast.makeText(this, error, Toast.LENGTH_LONG).show()
}