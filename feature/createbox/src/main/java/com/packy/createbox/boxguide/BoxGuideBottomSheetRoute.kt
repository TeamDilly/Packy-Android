package com.packy.createbox.boxguide

enum class BoxGuideBottomSheetRoute {
    ADD_GIFT,
    ADD_LATTER,
    ADD_MUSIC,
    ADD_PHOTO,
    ADD_STICKER_1,
    ADD_STICKER_2,
    CHANGE_BOX,
    EMPTY;


    fun showDim():Boolean = when(this){
        ADD_GIFT,
        ADD_LATTER,
        ADD_MUSIC,
        ADD_PHOTO,
        EMPTY -> true
        CHANGE_BOX,
        ADD_STICKER_1,
        ADD_STICKER_2 -> false
    }
}