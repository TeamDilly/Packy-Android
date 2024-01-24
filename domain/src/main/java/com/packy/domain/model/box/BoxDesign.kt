package com.packy.domain.model.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesign(
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
    @SerialName("boxFull") val boxFull: String,
    @SerialName("boxPart") val boxPart: String,
    @SerialName("boxBottom") val boxBottom: String
)
