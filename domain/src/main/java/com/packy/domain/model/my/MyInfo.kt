package com.packy.domain.model.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyInfo(
    @SerialName("nickname") val nickname: String?
)