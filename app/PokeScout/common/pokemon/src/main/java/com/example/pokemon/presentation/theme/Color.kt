package com.example.pokemon.presentation.theme

import androidx.compose.ui.graphics.Color

val MasterBallPurple = Color(0xff65418D)
val MasterBallPink = Color(0xffd580ac)
val GreatBallBlue = Color(0xff3b82c4)
val PokeBallRed = Color(0xffee1515)
val PokeBallWhite = Color(0xfff0f0f0)
val PokeBallGrey = Color(0xff222224)

val PokeBallDarkGrey = Color(0xFF181818)
val PokeBallLightGrey = Color(0xffd3d3d3)

data class PokeBallColors(
    val topHalfColor: Color,
    val bottomHalfColor: Color,
    val dividerColor: Color,
    val accentColor: Color,
    val emptyColor: Color,
)

val pokeBallColors: PokeBallColors = PokeBallColors(
    topHalfColor = PokeBallRed,
    bottomHalfColor = PokeBallWhite,
    dividerColor = PokeBallGrey,
    accentColor = PokeBallRed,
    emptyColor = PokeBallDarkGrey,
)

val greatBallColors: PokeBallColors = PokeBallColors(
    topHalfColor = GreatBallBlue,
    bottomHalfColor = PokeBallWhite,
    dividerColor = PokeBallGrey,
    accentColor = PokeBallRed,
    emptyColor = PokeBallDarkGrey,
)

val masterBallColors: PokeBallColors = PokeBallColors(
    topHalfColor = MasterBallPurple,
    bottomHalfColor = PokeBallWhite,
    dividerColor = PokeBallGrey,
    accentColor = MasterBallPink,
    emptyColor = PokeBallDarkGrey,
)