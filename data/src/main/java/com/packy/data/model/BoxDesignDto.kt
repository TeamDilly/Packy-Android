package com.packy.data.model

import com.packy.domain.model.box.BoxDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesignDto(
    @SerialName("id") val id: Int,
    @SerialName("boxTop") val boxTop: String,
    @SerialName("boxBottom") val boxBottom: String
)

fun BoxDesignDto.toEntity(): BoxDesign = BoxDesign(
    id = this.id,
    boxTopUri = this.boxTop,
    boxBottomUri = this.boxBottom
)