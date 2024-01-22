package com.packy.domain.model.box

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesign(
    @SerialName("id") val id: Int,
    @SerialName("boxTopUri") val boxTopUri: String,
    @SerialName("boxBottomUri") val boxBottomUri: String
)
