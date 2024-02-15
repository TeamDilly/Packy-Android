package com.packy.domain.model.createbox

data class SelectedSticker(
    var sticker1: Sticker?,
    var sticker2: Sticker?,
) {
    fun isStickerComplete() = this.sticker1 != null && this.sticker2 != null

    fun notContains(stickerId: Int): Boolean =
        this.sticker1?.id != stickerId && this.sticker2?.id != stickerId

    fun get(index: Int): Sticker? = when (index) {
        1 -> sticker1
        2 -> sticker2
        else -> null
    }

    fun isSelectedNumber(sticker: Sticker?): Int? = when (sticker) {
        sticker1 -> 1
        sticker2 -> 2
        else -> null
    }

    fun set(
        index: Int,
        sticker: Sticker?
    ) = this.apply {
        when (index) {
            1 -> sticker1 = sticker
            2 -> sticker2 = sticker
            else -> null
        }
    }

    fun isSelected(sticker: Sticker?): Boolean = sticker1 == sticker || sticker2 == sticker
}

