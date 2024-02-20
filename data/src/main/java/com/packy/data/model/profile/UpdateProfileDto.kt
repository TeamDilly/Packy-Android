package com.packy.data.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileDto(
    @SerialName("id") val id: Int,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("provider") val provider: String
)