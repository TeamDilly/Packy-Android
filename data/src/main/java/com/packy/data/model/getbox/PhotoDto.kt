package com.packy.data.model.getbox

import com.packy.domain.model.getbox.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("description") val description: String,
    @SerialName("photoUrl") val photoUrl: String,
    @SerialName("sequence") val sequence: Int
) {
    fun toEntity(): Photo = Photo(
        description = description,
        photoUrl = photoUrl,
        sequence = sequence
    )
}

fun List<PhotoDto>.toEntity() : List<Photo> = map { it.toEntity() }