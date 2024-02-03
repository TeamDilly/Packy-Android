package com.packy.domain.model.getbox

import com.packy.lib.ext.toEncoding
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("description") val description: String,
    @SerialName("photoUrl") val photoUrl: String,
    @SerialName("sequence") val sequence: Int
) {
    fun toUrlEncoding(): Photo = Photo(
        description = description,
        photoUrl = photoUrl.toEncoding(),
        sequence = sequence
    )
}