package com.example.leader.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.theme.PokeScoutTheme
import com.example.compose.theme.PokeballGrey
import com.example.compose.theme.PokeballRed
import com.example.compose.theme.PokeballWhite
import com.example.compose.theme.ThemeDarkGrey
import com.example.leader.presentation.events.InputEvent
import com.example.leader.presentation.ui.OptionCard
import com.example.leader.presentation.ui.PokeballScaffold
import com.example.leader.presentation.viewmodel.LeaderState
import com.example.pokemon.domain.model.GroupType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    state: LeaderState,
    onInputEvent: (InputEvent) -> Unit = {},
) {
    PokeScoutTheme {
        PokeballScaffold(
            PokeballRed,
            PokeballWhite,
            PokeballGrey,
            ThemeDarkGrey,
            state = state,
            onInputEvent = onInputEvent
        ) {

        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        LeaderState(
            isWritingNfc = true
        )
    )
}