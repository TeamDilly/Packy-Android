package com.packy.data.model.profile

import com.packy.domain.model.profile.ProfileDesign
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDesignDto(
    @SerialName("id") val id: Long,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("sequence") val sequence: Int
){
    fun toEntity(): ProfileDesign = ProfileDesign(
        id = id,
        imageUrl = imgUrl
    )
}