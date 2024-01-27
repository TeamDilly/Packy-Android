package com.packy.di.authenticator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Int,
    @SerialName("grantType") val grantType: String,
    @SerialName("refreshToken") val refreshToken: String
)