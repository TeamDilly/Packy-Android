package com.packy.core.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.packy.core.R
import javax.annotation.concurrent.Immutable

private val PackyFontStyle = TextStyle(
    fontFamily = FontFamily(
        Font(
            resId = R.font.pretendard_bold,
            weight = FontWeight.Bold,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.pretendard_regular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        ),
    ),
    platformStyle = PlatformTextStyle(
        includeFontPadding = false
    )
)

interface PackyTypography {
    val heading01: TextStyle
    val title01: TextStyle
    val title02: TextStyle
    val title03: TextStyle
    val body01: TextStyle
    val body02: TextStyle
    val body03: TextStyle
    val body04: TextStyle
    val body05: TextStyle
    val body06: TextStyle
}

val defaultPackyTypography = object : PackyTypography {
    override val heading01: TextStyle = PackyFontStyle.copy(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
    override val title01: TextStyle = PackyFontStyle.copy(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
    override val title02: TextStyle = PackyFontStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
    override val title03: TextStyle = PackyFontStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal
    )
    override val body01: TextStyle = PackyFontStyle.copy(
        fontSize = 18.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
    override val body02: TextStyle = PackyFontStyle.copy(
        fontSize = 16.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal
    )
    override val body03: TextStyle = PackyFontStyle.copy(
        fontSize = 16.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal
    )
    override val body04: TextStyle = PackyFontStyle.copy(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal
    )
    override val body05: TextStyle = PackyFontStyle.copy(
        fontSize = 14.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal
    )
    override val body06: TextStyle = PackyFontStyle.copy(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal
    )
}

@SuppressLint("CompositionLocalNaming")
internal val PackyTypographyScheme = staticCompositionLocalOf { defaultPackyTypography }