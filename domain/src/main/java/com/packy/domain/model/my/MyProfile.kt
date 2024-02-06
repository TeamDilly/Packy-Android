package com.packy.domain.model.my

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyProfile(
    @SerialName("id") val id: Int,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("nickname") val nickname: String,
)
