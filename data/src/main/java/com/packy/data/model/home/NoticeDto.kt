package com.packy.data.model.home

import androidx.compose.foundation.Image
import com.packy.domain.model.banner.ImageBanner
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoticeDto(
    @SerialName("imgUrl") val imgUrl: String,
    @SerialName("noticeUrl") val noticeUrl: String,
) {
    fun toEntity(): ImageBanner = ImageBanner(
        imageUrl = imgUrl,
        url = noticeUrl,
    )
}