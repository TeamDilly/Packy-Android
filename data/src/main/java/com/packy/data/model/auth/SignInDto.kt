package com.packy.data.model.auth

import com.packy.domain.model.auth.SignIn
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInDto(
    @SerialName("status") val status: String,
    @SerialName("tokenInfo") val tokenInfo: TokenInfo? = null,
)

fun SignInDto.toEntity() = SignIn(
    status = this.status,
    memberId = this.tokenInfo?.memberId
)