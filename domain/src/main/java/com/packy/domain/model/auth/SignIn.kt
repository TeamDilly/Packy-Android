package com.packy.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignIn(
    @SerialName("status") val status: String
)