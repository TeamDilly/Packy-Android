package com.packy.domain.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUp(
    val token: String,
    val provider: String,
    val nickname: String,
    val profileImg: Int,
    val marketingAgreement: Boolean,
    val pushNotification: Boolean,
    val serviceAllow: Boolean,
    val personalAllow: Boolean
) {
    fun isAvailable() = token.isNotBlank() && provider.isNotBlank() && serviceAllow && personalAllow
}