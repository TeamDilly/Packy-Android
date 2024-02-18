package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchiveMusic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveMusicDto(
   @SerialName("giftBoxId") val giftBoxId: Int,
   @SerialName("youtubeUrl") val youtubeUrl: String
) {
    fun toEntity(thumbnailUrl: String): ArchiveMusic = ArchiveMusic(
        thumbnailUrl = thumbnailUrl,
        youtubeUrl = youtubeUrl
    )
}