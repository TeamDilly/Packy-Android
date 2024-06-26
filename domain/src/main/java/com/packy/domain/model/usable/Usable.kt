package com.packy.domain.model.usable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Usable(
    @SerialName("isAvailable") val isAvailable: Boolean,
    @SerialName("memberId") val memberId: Long,
    @SerialName("reason") val reason: UsableStatus?
)