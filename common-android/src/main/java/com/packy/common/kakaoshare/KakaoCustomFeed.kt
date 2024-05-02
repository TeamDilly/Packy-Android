package com.packy.common.kakaoshare

data class KakaoCustomFeed(
    val sender: String,
    val receiver: String,
    val imageUrl: String,
    val boxId: Long
){
    fun toFeedArgs() = mapOf(
        "sender" to sender,
        "receiver" to receiver,
        "image_url" to imageUrl,
        "boxId" to boxId.toString()
    )
}
