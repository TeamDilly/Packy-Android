package com.packy.data.model.home

import com.packy.domain.model.settings.SettingItem
import com.packy.domain.model.settings.SettingRoute
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SettingDto(
    @SerialName("tag") val tag: SettingRoute,
    @SerialName("url") val url: String
){
    fun toEntity() : SettingItem = SettingItem(
        route = tag,
        url = url
    )
}