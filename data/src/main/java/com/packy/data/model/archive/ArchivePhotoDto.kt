package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchivePhoto
import kotlinx.serialization.Serializable

@Serializable
data class ArchivePhotoDto(
    val description: String,
    val id: Int,
    val photoUrl: String
) {
    fun toEntity(): ArchivePhoto = ArchivePhoto(
        photoUrl = photoUrl,
        photoContentText = description
    )
}