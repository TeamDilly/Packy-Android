package com.packy.lib.ext

import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


fun String.isURLValid(): Boolean {
    return try {
        URL(this)
        true // URL 형식이 맞으면 true 반환
    } catch (e: Exception) {
        false // URL 형식이 아니면 false 반환
    }
}

fun String.toEncoding(): String {
    return if(this.isURLValid()) URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
    else this
}
