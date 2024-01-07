package com.packy.data.model.example

import com.packy.domain.model.example.SupportDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Support(
    @SerialName("text") val text: String,
    @SerialName("url") val url: String
)

fun Support.toDto() = SupportDto(text, url)