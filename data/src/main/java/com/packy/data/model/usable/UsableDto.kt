package com.packy.domain.model.usable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsableDto(
    @SerialName("isAvailable") val isAvailable: Boolean,
    @SerialName("id") val memberId: Long,
    @SerialName("reason") val reason: UsableStatus? = null,
) {
    fun toEntity(): Usable = Usable(
        isAvailable = isAvailable,
        reason = reason,
        memberId = memberId
    )
}

