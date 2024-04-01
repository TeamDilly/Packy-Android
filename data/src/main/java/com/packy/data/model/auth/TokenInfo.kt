package com.packy.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    @SerialName("id") val memberId: Long,
    @SerialName("accessToken") val accessToken: String? = null,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Long? = null,
    @SerialName("grantType") val grantType: String? = null,
    @SerialName("refreshToken") val refreshToken: String? = null
)