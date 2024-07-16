package com.example.developer.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.developer.presentation.components.MainView
import com.example.developer.presentation.viewmodel.DeveloperViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DeveloperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(viewModel.state) {
                viewModel.processInputEvent(it)
            }
        }
    }
}
