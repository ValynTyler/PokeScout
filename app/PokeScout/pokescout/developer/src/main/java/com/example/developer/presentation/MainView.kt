package com.example.developer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.scaffold.PokeScoutScaffold
import com.example.developer.presentation.input.InputEvent
import com.example.developer.presentation.ui.BadgeList
import com.example.developer.presentation.ui.DaysInputRow
import com.example.developer.presentation.ui.GroupDropdown
import com.example.developer.presentation.ui.IdInput
import com.example.developer.presentation.ui.NameInput
import com.example.developer.presentation.viewmodel.DeveloperState

@Composable
fun MainView(
    state: DeveloperState,
    onInput: (InputEvent) -> Unit = {},
) {
    PokeScoutScaffold(
        onFabPress = { onInput(InputEvent.LockEvent) }
    ) {
        Column {
            Row {
                NameInput(state, Modifier.weight(1f)) {
                    onInput(
                        InputEvent.TextEvent.ChangeTrainer(
                            it
                        )
                    )
                }
                GroupDropdown(
                    modifier = Modifier.weight(1f),
                    type = state.inputData.groupType
                ) { onInput(InputEvent.GroupChanged(it)) }
            }
            IdInput(
                state,
                { onInput(InputEvent.TextEvent.ChangeSpecies(it)) },
                { onInput(InputEvent.TextEvent.ChangeEvolutionChain(it)) },
            )
            BadgeList(state) { index, checked ->
                onInput(InputEvent.ListEvent(index, checked))
            }
            DaysInputRow(state) { index, value ->
                onInput(InputEvent.DayEvent(index, value))
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview() {
    MainView(
        state = DeveloperState()
    )
}