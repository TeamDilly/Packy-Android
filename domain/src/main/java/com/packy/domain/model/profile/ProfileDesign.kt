package com.packy.domain.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDesign(
    @SerialName("id") val id: Long,
    @SerialName("imgUrl") val imageUrl: String
)
