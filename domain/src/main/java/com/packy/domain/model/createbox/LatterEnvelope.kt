package com.packy.domain.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatterEnvelope(
    @SerialName("envelope") val envelope: String,
    @SerialName("id") val id: Int,
    @SerialName("letterPaper") val letterPaper: String
)