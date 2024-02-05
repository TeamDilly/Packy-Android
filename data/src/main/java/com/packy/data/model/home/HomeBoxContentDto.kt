package com.packy.data.model.home

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeBoxContentDto(
    @SerialName("content") val content: List<HomeBoxDto>,
    @SerialName("first") val first: Boolean,
    @SerialName("last") val last: Boolean
)