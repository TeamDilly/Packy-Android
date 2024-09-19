package com.packy.domain.model.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingItem(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
