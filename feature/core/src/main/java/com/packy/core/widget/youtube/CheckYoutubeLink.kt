package com.packy.core.widget.youtube

fun extractYouTubeVideoId(url: String): String? {
    val videoIdKey = "v="
    val index = url.indexOf(videoIdKey)

    return if (index != -1) {
        val startIndex = index + videoIdKey.length
        val endIndex = url.indexOf("&", startIndex).takeIf { it != -1 } ?: url.length
        url.substring(startIndex, endIndex)
    } else {
        null
    }
}

fun String.validationYoutubeVideoId() = extractYouTubeVideoId(this) != null