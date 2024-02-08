package com.packy.common.authenticator.ext

import android.net.Uri
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