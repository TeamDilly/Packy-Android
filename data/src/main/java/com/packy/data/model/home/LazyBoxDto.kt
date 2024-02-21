package com.packy.data.model.home

import com.packy.domain.model.home.LazyBox
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LazyBoxDto(
    @SerialName("createdAt") val createdAt: String,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("receiverName") val receiverName: String,
    @SerialName("smallImgUrl") val smallImgUrl: String
){
    fun toEntity(): LazyBox = LazyBox(
        boxId = id,
        boxTitle = name,
        receiverName = receiverName,
        boxImageUrl = smallImgUrl,
        createdAt = createdAt
    )
}