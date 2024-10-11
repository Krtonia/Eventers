package com.example.eventer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.eventer.R

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

val firasans = FontFamily(
    Font(R.font.firasans_black, FontWeight.Black),
    Font(R.font.firasans_blackitalic, FontWeight.Normal),
    Font(R.font.firasans_bold, FontWeight.Bold),
    Font(R.font.firasans_bolditalic, FontWeight.Bold),
    Font(R.font.firasans_extrabold, FontWeight.ExtraBold),
    Font(R.font.firasans_extrabolditalic, FontWeight.ExtraBold),
    Font(R.font.firasans_italic, FontWeight.Normal),
    Font(R.font.firasans_light, FontWeight.Light),
    Font(R.font.firasans_lightitalic, FontWeight.Light),
    Font(R.font.firasans_medium, FontWeight.Medium),
    Font(R.font.firasans_mediumitalic, FontWeight.Medium),
    Font(R.font.firasans_regular, FontWeight.Normal),
    Font(R.font.firasans_semibold, FontWeight.SemiBold),
    Font(R.font.firasans_semibolditalic, FontWeight.SemiBold),
    Font(R.font.firasans_thin, FontWeight.Thin),
    Font(R.font.firasans_thinitalic, FontWeight.Thin),
)

