package com.packy.domain.model.youtube

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeInfo (
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("title") val title: String
)