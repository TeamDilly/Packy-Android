package com.packy.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Long,
    @SerialName("grantType") val grantType: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("id") val memberId: Long
)