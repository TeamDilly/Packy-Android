package com.packy.common.authenticator.ext

import android.graphics.Color.parseColor
import android.net.Uri
import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.removeQueryParameters(): String {
    val uri = Uri.parse(this)
    return uri.buildUpon().clearQuery().toString()
}

fun String.toFormatTimeStampString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")
    val dateTime = LocalDateTime.parse(this)
    return dateTime.format(formatter)
}

fun String?.colorCodeToColor(
    fallbackColor: Color,
    alpha: Float = 0f
): Color {
    val colorString = if (this?.startsWith("#") == true) {
        // Remove "#" if present
        this.substring(1)
    } else {
        this
    }
    val color = try {
        Color(color = parseColor("#${colorString?.trim()}")).copy(alpha = alpha)
    } catch (e: Exception) {
        e.stackTrace
        fallbackColor
    }
    return color
}