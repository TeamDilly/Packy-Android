package com.packy.data.model.archive

import com.packy.domain.model.archive.ArchivePhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArchivePhotoDto(
    @SerialName("description") val description: String,
    @SerialName("id") val id: Int,
    @SerialName("photoUrl") val photoUrl: String
) {
    fun toEntity(): ArchivePhoto = ArchivePhoto(
        photoUrl = photoUrl,
        photoContentText = description
    )
}