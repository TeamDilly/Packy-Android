package com.packy.data.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBoxDto(
    @SerialName("id") val id: Long,
    @SerialName("uuid") val uuid: String,
)