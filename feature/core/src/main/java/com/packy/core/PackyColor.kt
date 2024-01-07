package com.packy.core

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class PackyColor(
    val black: Color = Color(0xFF000000),
    val black900: Color = Color(0xFF222222),
    val black800: Color = Color(0xFF444444),
    val black700: Color = Color(0xFF717171),
    val black600: Color = Color(0xFF888888),
    val black500: Color = Color(0xFFA6A6A6),
    val black400: Color = Color(0xFFBBBBBB),
    val black300: Color = Color(0xFFDDDDDD),
    val black200: Color = Color(0xFFE9E9E9),
    val black100: Color = Color(0xFFF3F3F3),
    val white: Color  = Color(0xFFFFFFFF),
    val purple800: Color = Color(0xFF2F2F7C),
    val purple700: Color = Color(0xFF3C37A4),
    val purple600: Color = Color(0xFF4545D1),
    val purple500: Color = Color(0xFF635DF0),
    val purple400: Color = Color(0xFF7979F7),
    val purple300: Color = Color(0xFFA4A1FF),
    val purple200: Color = Color(0xFFCACAFC),
    val purple100: Color = Color(0xFFEBEAFF),
    val pink800: Color = Color(0xFFAF3893),
    val pink700: Color = Color(0xFFD44B81),
    val pink600: Color = Color(0xFFED76A5),
    val pink500: Color = Color(0xFFF594BA),
    val pink400: Color = Color(0xFFFFB2D0),
    val pink300: Color = Color(0xFFFFC5DC),
    val pink200: Color = Color(0xFFFFD5E5),
    val pink100: Color = Color(0xFFFFE8F1),
)

internal val PackyColorScheme = staticCompositionLocalOf { PackyColor() }