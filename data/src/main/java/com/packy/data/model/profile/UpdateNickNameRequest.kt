package com.packy.data.model.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNickNameRequest(
    @SerialName("nickname") val nickname: String
)