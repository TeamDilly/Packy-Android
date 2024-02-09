package com.packy.lib.ext

import java.net.URI


fun extractYouTubeVideoId(url: String): String? {
    val videoIdKey = "v="
    val shortUrlKey = "youtu.be/"

    val index = url.indexOf(videoIdKey)
    val shortIndex = url.indexOf(shortUrlKey)

    return when {
        index != -1 -> {
            val startIndex = index + videoIdKey.length
            val endIndex = url.indexOf("&", startIndex).takeIf { it != -1 } ?: url.length
            url.substring(startIndex, endIndex)
        }
        shortIndex != -1 -> {
            val startIndex = shortIndex + shortUrlKey.length
            val endIndex = url.indexOf("&", startIndex).takeIf { it != -1 } ?: url.length
            url.substring(startIndex, endIndex)
        }
        else -> null
    }
}

fun String.validationYoutubeVideoId() = extractYouTubeVideoId(this) != null

fun String.removeNewlines(): String {
    return this.replace(
        Regex("[\n\r]"),
        ""
    )
}