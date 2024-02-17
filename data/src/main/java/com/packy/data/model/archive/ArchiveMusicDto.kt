package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchiveMusic
import kotlinx.serialization.Serializable

@Serializable
data class ArchiveMusicDto(
    val giftBoxId: Int,
    val youtubeUrl: String
) {
    fun toEntity(thumbnailUrl: String): ArchiveMusic = ArchiveMusic(
        thumbnailUrl = thumbnailUrl,
        youtubeUrl = youtubeUrl
    )
}