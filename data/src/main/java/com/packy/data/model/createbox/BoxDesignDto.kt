package com.packy.data.model.createbox

import com.packy.domain.model.box.BoxDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesignDto(
    @SerialName("id") val id: Int,
    @SerialName("sequence") val sequence: Int,
    @SerialName("boxNormal") val boxNormal: String,
    @SerialName("boxSet") val boxSet: String,
    @SerialName("boxSmall") val boxSmall: String,
    @SerialName("boxTop") val boxTop: String
)

fun BoxDesignDto.toEntity(): BoxDesign = BoxDesign(
    id = id,
    sequence = sequence,
    boxNormal = boxNormal,
    boxSet = boxSet,
    boxSmall = boxSmall,
    boxTop = boxTop
)

