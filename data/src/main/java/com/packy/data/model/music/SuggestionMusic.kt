package com.packy.data.model.music

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class SuggestionMusic(
    @SerialName("hashtags") val hashtags: List<String>,
    @SerialName("id") val id: Int,
    @SerialName("youtubeUrl") val youtubeUrl: String
)