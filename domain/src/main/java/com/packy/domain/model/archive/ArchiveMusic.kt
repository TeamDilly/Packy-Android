package com.packy.domain.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchiveMusic(
    val thumbnailUrl: String,
    val youtubeUrl: String
)
