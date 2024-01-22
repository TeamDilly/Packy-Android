package com.packy.domain.model.music

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Music(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("hashtags") val hashtags: List<String>,
    @SerialName("youtubeUri") val youtubeUri: String
)