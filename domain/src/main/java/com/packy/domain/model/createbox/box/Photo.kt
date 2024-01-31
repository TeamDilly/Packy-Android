package com.packy.domain.model.createbox.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("description") val description: String?,
    @SerialName("photoUrl") val photoUrl: String?,
    @SerialName("sequence") val sequence: Int
)