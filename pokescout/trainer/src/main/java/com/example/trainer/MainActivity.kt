package com.example.trainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.common.ui.Greeting
import com.example.common.ui.PokemonImage
import com.example.trainer.ui.theme.PokeScoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeScoutTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "PokeScout Trainer",
                        modifier = Modifier.padding(innerPadding)
                    )
                    Column(verticalArrangement =  Arrangement.Center, modifier = Modifier.fillMaxSize()) {
                        PokemonImage(id = 1, modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.DarkGray)
                        )
                    }
                }
            }
        }
    }
}