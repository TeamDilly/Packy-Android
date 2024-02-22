package com.packy.domain.model.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesign(
    @SerialName("id") val id: Long,
    @SerialName("sequence") val sequence: Int,
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("boxSet") val boxSet: String,
    @SerialName("boxSmall") val boxSmall: String,
    @SerialName("boxTop") val boxTop: String
)
