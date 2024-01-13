package com.packy.data.model.youtube

import com.packy.domain.model.youtube.YoutubeInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeInfoDto(
    @SerialName("author_name") val authorName: String,
    @SerialName("author_url") val authorUrl: String,
    @SerialName("height") val height: Int,
    @SerialName("html") val html: String,
    @SerialName("provider_name") val providerName: String,
    @SerialName("provider_url") val providerUrl: String,
    @SerialName("thumbnail_height") val thumbnailHeight: Int,
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    @SerialName("thumbnail_width") val thumbnailWidth: Int,
    @SerialName("title") val title: String,
    @SerialName("type") val type: String,
    @SerialName("version") val version: String,
    @SerialName("width") val width: Int
)

fun YoutubeInfoDto.toEntity() = YoutubeInfo(
    thumbnail = this.thumbnailUrl,
    title = this.title
)