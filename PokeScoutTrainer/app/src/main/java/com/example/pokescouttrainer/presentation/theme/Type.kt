package com.example.pokescouttrainer.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.pokescouttrainer.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

// Custom fonts
val pokeSansFamily = FontFamily(
    Font(R.font.pokemon_hollow, FontWeight.Light),
    Font(R.font.pokemon_hollow, FontWeight.Normal),
    Font(R.font.pokemon_solid, FontWeight.Medium),
    Font(R.font.pokemon_solid, FontWeight.Bold)
)

val futuraExtraBoldFamily = FontFamily(
    Font(R.font.futura_extra_bold, FontWeight.Normal),
)
