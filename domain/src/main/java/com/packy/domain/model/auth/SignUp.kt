package com.packy.domain.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUp(
    @SerialName("token") val token: String,
    @SerialName("provider") val provider: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImg") val profileImg: Long,
    @SerialName("marketingAgreement") val marketingAgreement: Boolean,
    @SerialName("pushNotification") val pushNotification: Boolean,
    @SerialName("serviceAllow") val serviceAllow: Boolean,
    @SerialName("personalAllow") val personalAllow: Boolean
) {
    fun isAvailable() = token.isNotBlank() && provider.isNotBlank() && serviceAllow && personalAllow
}