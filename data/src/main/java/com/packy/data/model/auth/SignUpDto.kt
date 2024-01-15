package com.packy.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(
    val accessToken: String,
    val accessTokenExpiresIn: Int,
    val grantType: String,
    val refreshToken: String
)