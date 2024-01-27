package com.packy.data.model.createbox

data class StickerDto(
    val content: List<StickerContent>,
    val first: Boolean,
    val last: Boolean
)