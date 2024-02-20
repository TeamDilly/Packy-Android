package com.packy.data.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("profileImg") val profileImg: Int
)