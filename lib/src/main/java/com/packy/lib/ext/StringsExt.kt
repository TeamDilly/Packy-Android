package com.packy.lib.ext

fun extractYouTubeVideoId(url: String): String? {
    val videoIdKey = "v="
    val shortUrlKey = "youtu.be/"
    val shareUrlKey = "youtu.be/share/"

    val index = url.indexOf(videoIdKey)
    val shortIndex = url.indexOf(shortUrlKey)
    val shareIndex = url.indexOf(shareUrlKey)

    return when {
        index != -1 -> {
            val endIndex = url.indexOf("&", index + videoIdKey.length).takeIf { it != -1 } ?: url.indexOf("?", index + videoIdKey.length).takeIf { it != -1 } ?: url.length
            url.substring(index + videoIdKey.length, endIndex)
        }
        shortIndex != -1 -> {
            val endIndex = url.indexOf("&", shortIndex + shortUrlKey.length).takeIf { it != -1 } ?: url.indexOf("?", shortIndex + shortUrlKey.length).takeIf { it != -1 } ?: url.length
            url.substring(shortIndex + shortUrlKey.length, endIndex)
        }
        shareIndex != -1 -> {
            val endIndex = url.indexOf("&", shareIndex + shareUrlKey.length).takeIf { it != -1 } ?: url.indexOf("?", shareIndex + shareUrlKey.length).takeIf { it != -1 } ?: url.length
            url.substring(shareIndex + shareUrlKey.length, endIndex)
        }
        else -> null
    }
}

fun String.validationYoutubeVideoId() = extractYouTubeVideoId(this) != null

fun String.youtubeIdToThumbnailUrl(): String? {
    val videoId = extractYouTubeVideoId(this)
    return if (videoId != null) {
        "https://img.youtube.com/vi/$videoId/mqdefault.jpg"
    } else {
        null
    }
}

fun String.removeNewlines(): String {
    return this.replace(
        Regex("[\n\r]"),
        ""
    )
}