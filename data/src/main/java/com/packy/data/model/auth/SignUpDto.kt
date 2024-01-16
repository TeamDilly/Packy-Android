package com.packy.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Int,
    @SerialName("grantType") val grantType: String,
    @SerialName("refreshToken") val refreshToken: String
)