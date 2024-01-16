package com.packy.data.model.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("marketingAgreement") val marketingAgreement: Boolean,
    @SerialName("nickname") val nickname: String,
    @SerialName("profileImg") val profileImg: Int,
    @SerialName("provider") val provider: String,
    @SerialName("pushNotification") val pushNotification: Boolean
)