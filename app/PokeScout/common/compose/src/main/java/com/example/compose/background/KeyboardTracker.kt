package com.example.compose.background

import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun KeyboardTracker(
    focusManager: FocusManager
) {
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    var keyboardUpLf by remember { mutableStateOf(false) }
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val keyboardUp = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            if (keyboardUpLf != keyboardUp) {
                if (!keyboardUp) {
                    focusManager.clearFocus()
                }
            }
            keyboardUpLf = keyboardUp
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
}