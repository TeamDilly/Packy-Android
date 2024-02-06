package com.packy.data.model.my

import com.packy.domain.model.my.MyProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyProfileDto(
    @SerialName("id") val id: Int,
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("nickname") val nickname: String,
    @SerialName("provider") val provider: String
){
    fun toEntity() = MyProfile(
        id = id,
        imgUrl = imgUrl,
        nickname = nickname
    )
}