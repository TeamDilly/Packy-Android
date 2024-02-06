package com.packy.domain.model.settings

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingItem(
    @SerialName("route") val route: SettingRoute,
    @SerialName("url") val url: String
)
