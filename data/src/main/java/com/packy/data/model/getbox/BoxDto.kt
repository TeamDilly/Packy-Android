package com.packy.data.model.getbox

import com.packy.domain.model.getbox.Box
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDto(
    @SerialName("id") val id: Long,
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("boxTop") val boxTop: String,
)

fun BoxDto.toEntity(): Box =
    Box(
        boxNormal = boxNormal,
        boxTop = boxTop,
        id = id
    )