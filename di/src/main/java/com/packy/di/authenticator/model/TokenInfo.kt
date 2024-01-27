package com.packy.di.authenticator.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Long,
    @SerialName("grantType") val grantType: String,
    @SerialName("refreshToken") val refreshToken: String
)

@Serializable
data class RefreshTokenRequest(
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String
)