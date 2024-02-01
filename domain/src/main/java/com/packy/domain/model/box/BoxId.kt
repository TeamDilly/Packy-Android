package com.packy.domain.model.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxId(
    @SerialName("id") val id: String
)
