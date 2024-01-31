package com.packy.createbox.common

import com.packy.domain.model.createbox.box.Gift
import com.packy.domain.model.createbox.box.Photo
import com.packy.domain.model.createbox.box.Stickers
import com.packy.domain.usecase.createbox.CreateBoxUseCase

suspend fun CreateBoxUseCase.boxDesign(id: Int?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(boxId = id))
}

suspend fun CreateBoxUseCase.letterContent(letterContent: String?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(letterContent = letterContent))
}

suspend fun CreateBoxUseCase.envelopId(id: Int?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(envelopeId = id))
}

suspend fun CreateBoxUseCase.youtubeUrl(url: String?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(youtubeUrl = url))
}

suspend fun CreateBoxUseCase.sticker(
    id: Int?,
    location: Int?
) {
    val createBox = getCreatedBox()
    val clearSticker = createBox.stickers.toMutableList()
    clearSticker.removeIf { it.location == location }
    clearSticker.add(
        Stickers(
            id = id,
            location = location
        )
    )
    createBox(createBox.copy(stickers = clearSticker))
}

suspend fun CreateBoxUseCase.gift(gift: Gift?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(gift = gift))
}

suspend fun CreateBoxUseCase.name(name: String?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(name = name))
}

suspend fun CreateBoxUseCase.receiverName(receiverName: String?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(receiverName = receiverName))
}

suspend fun CreateBoxUseCase.senderName(senderName: String?) {
    val createBox = getCreatedBox()
    createBox(createBox.copy(senderName = senderName))
}

suspend fun CreateBoxUseCase.photo(photos: List<Photo>){
    val createBox = getCreatedBox()
    createBox(createBox.copy(photos = photos))
}