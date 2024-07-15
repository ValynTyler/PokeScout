package com.example.pokescoutdeveloper.presentation.components

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pokescoutdeveloper.presentation.DeveloperState
import com.example.pokescoutdeveloper.presentation.events.InputEvent

@Composable
fun MainView(
    state: DeveloperState,
    onInputEvent: (InputEvent) -> Unit = {},
) {

    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

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

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { focusManager.clearFocus() }
    ) {
        TextInput(
            labelText = "Trainer Name",
            value = state.inputName,
            enabled = !state.isWritingNfc,
        ) { onInputEvent(InputEvent.TextEvent.ChangedName(it)) }
        NumberInput(
            labelText = "ID",
            value = state.inputId?.toString().orEmpty(),
            enabled = !state.isWritingNfc,
        ) { onInputEvent(InputEvent.TextEvent.ChangedId(it)) }
        NumberInput(
            labelText = "XP",
            value = state.inputXp?.toString().orEmpty(),
            enabled = !state.isWritingNfc,
        ) { onInputEvent(InputEvent.TextEvent.ChangedXp(it)) }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
    ) {
        LockButton(
            modifier = Modifier.padding(72.dp),
            onClick = { onInputEvent(InputEvent.LockEvent) },
        )
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        state = DeveloperState(
            inputXp = 69,
            inputId = 42,
            inputName = "Jorbo"
        )
    )
}