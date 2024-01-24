package com.packy.data.model.createbox

import com.packy.domain.model.box.BoxDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesignDto(
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
    @SerialName("boxFull") val boxFull: String,
    @SerialName("boxPart") val boxPart: String,
    @SerialName("boxBottom") val boxBottom: String
)

fun BoxDesignDto.toEntity(): BoxDesign = BoxDesign(
    id = id,
    sequence = sequence,
    boxFull = boxFull,
    boxPart = boxPart,
    boxBottom = boxBottom
)