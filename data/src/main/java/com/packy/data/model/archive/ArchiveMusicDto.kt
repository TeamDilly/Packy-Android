package com.packy.data.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchiveMusicDto(
    val giftBoxId: Int,
    val youtubeUrl: String
)