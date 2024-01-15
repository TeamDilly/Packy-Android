package com.packy.data.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val marketingAgreement: Boolean,
    val nickname: String,
    val profileImg: Int,
    val provider: String,
    val pushNotification: Boolean
)