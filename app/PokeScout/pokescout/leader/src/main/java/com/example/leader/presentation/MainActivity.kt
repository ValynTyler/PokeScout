package com.example.leader.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.leader.presentation.viewmodel.LeaderViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: LeaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(viewModel.state) { viewModel.onInputEvent(it) }
        }
    }
}
