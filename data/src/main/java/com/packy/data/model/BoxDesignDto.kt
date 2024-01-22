package com.packy.data.model

import com.packy.domain.model.box.BoxDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoxDesignDto(
    @SerialName("id") val id: Int,
    @SerialName("boxTopUri") val boxTopUri: String,
    @SerialName("boxBottomUri") val boxBottomUri: String
)

fun BoxDesignDto.toEntity(): BoxDesign = BoxDesign(
    id = this.id,
    boxTopUri = this.boxTopUri,
    boxBottomUri = this.boxBottomUri
)