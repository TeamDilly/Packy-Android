package com.packy.createbox.common

import com.packy.domain.model.createbox.box.Gift
import com.packy.domain.model.createbox.box.Photo
import com.packy.domain.model.createbox.box.Stickers
import com.packy.domain.usecase.createbox.CreateBoxUseCase

suspend fun CreateBoxUseCase.boxDesign(id: Long?, boxImage: String?, lottieAnimation: String) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(boxId = id, boxImage = boxImage, lottieAnimation = lottieAnimation))
}

suspend fun CreateBoxUseCase.letterContent(letterContent: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(letterContent = letterContent))
}

suspend fun CreateBoxUseCase.envelop(id: Int?, imageUri: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(envelopeId = id, envelopeUrl = imageUri))
}

suspend fun CreateBoxUseCase.youtubeUrl(url: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(youtubeUrl = url))
}

suspend fun CreateBoxUseCase.sticker(
    id: Int?,
    location: Int?,
    imageUri: String?
) {
    val createBox = getCreatedBox()
    val clearSticker = createBox.stickers.toMutableList()
    clearSticker.removeIf { it.location == location }
    clearSticker.add(
        Stickers(
            id = id,
            location = location,
            imageUri = imageUri
        )
    )
    setCreateBox(createBox.copy(stickers = clearSticker))
}

suspend fun CreateBoxUseCase.gift(gift: Gift?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(gift = gift))
}

suspend fun CreateBoxUseCase.name(name: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(name = name))
}

suspend fun CreateBoxUseCase.receiverName(receiverName: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(receiverName = receiverName))
}

suspend fun CreateBoxUseCase.senderName(senderName: String?) {
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(senderName = senderName))
}

suspend fun CreateBoxUseCase.photo(photo: Photo){
    val createBox = getCreatedBox()
    setCreateBox(createBox.copy(photo = photo))
}