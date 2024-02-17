package com.packy.data.model.archive

import kotlinx.serialization.Serializable

@Serializable
data class ArchivePhotoDto(
    val description: String,
    val id: Int,
    val photoUrl: String
)