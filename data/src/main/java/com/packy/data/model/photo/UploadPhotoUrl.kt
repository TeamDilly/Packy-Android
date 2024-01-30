package com.packy.data.model.photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadPhotoUrl(
    @SerialName("url") val url: String
)