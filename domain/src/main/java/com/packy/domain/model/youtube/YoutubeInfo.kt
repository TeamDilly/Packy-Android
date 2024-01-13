package com.packy.domain.model.youtube

import kotlinx.serialization.Serializable

@Serializable
data class YoutubeInfo (
    val thumbnail: String,
    val title: String
)