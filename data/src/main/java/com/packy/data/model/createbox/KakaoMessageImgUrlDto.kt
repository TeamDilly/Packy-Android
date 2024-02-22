package com.packy.data.model.createbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoMessageImgUrlDto(
    @SerialName("kakaoMessageImgUrl") val kakaoMessageImgUrl: String
)
