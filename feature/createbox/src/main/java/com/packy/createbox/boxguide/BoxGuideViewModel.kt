package com.packy.createbox.boxguide

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Strings
import com.packy.createbox.common.boxDesign
import com.packy.createbox.common.envelopId
import com.packy.createbox.common.letterContent
import com.packy.createbox.common.sticker
import com.packy.createbox.common.youtubeUrl
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.box.Stickers
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.domain.usecase.photo.UploadImageUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor(
    private val getLetterSenderReceiverUseCase: GetLetterSenderReceiverUseCase,
    private val getBoxDesignUseCase: GetBoxDesignUseCase,
    private val createBoxUseCase: CreateBoxUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
) : MviViewModel<BoxGuideIntent, BoxGuideState, BoxGuideEffect>() {
    override fun createInitialState(): BoxGuideState = BoxGuideState(
        title = "",
        photo = null,
        letter = null,
        youtubeUrl = null,
        selectedSticker = SelectedSticker(
            sticker1 = null,
            sticker2 = null
        ),
        gift = null,
        boxDesign = null
    )

    override fun handleIntent() {
        subscribeIntent<BoxGuideIntent.OnBackClick> { sendEffect(BoxGuideEffect.MoveToBack) }
        subscribeIntent<BoxGuideIntent.OnSaveClick>(::createBox)
        subscribeIntent<BoxGuideIntent.ShowBottomSheet> { sendEffect(BoxGuideEffect.ShowBottomSheet(it.boxGuideBottomSheetRoute)) }
        subscribeStateIntent<BoxGuideIntent.SavePhoto>(savePhoto())
        subscribeStateIntent<BoxGuideIntent.SaveLetter>(saveLetterBoxGuideState())
        subscribeStateIntent<BoxGuideIntent.SaveMusic>(saveYoutubeMusic())
        subscribeStateIntent<BoxGuideIntent.ClearMusic>(clearYoutubeMusic())
        subscribeStateIntent<BoxGuideIntent.SaveSticker>(saveSticker())
        subscribeStateIntent<BoxGuideIntent.SaveGift>(saveGift())
        subscribeStateIntent<BoxGuideIntent.SaveBox>(saveBox())
    }

    private suspend fun createBox(boxGuideIntent: BoxGuideIntent) {

    }

    private fun saveBox(): suspend (BoxGuideState, BoxGuideIntent.SaveBox) -> BoxGuideState =
        { state, intent ->
            createBoxUseCase.boxDesign(intent.boxDesign.id)
            state.copy(boxDesign = intent.boxDesign)
        }

    private fun saveGift(): suspend (BoxGuideState, BoxGuideIntent.SaveGift) -> BoxGuideState =
        { state, intent ->

            state.copy(gift = intent.imageUri)
        }

    private fun saveSticker(): suspend (BoxGuideState, BoxGuideIntent.SaveSticker) -> BoxGuideState =
        { state, intent ->
            createBoxUseCase.sticker(
                intent.sticker?.id,
                intent.index
            )

            when (intent.index) {
                1 -> state.copy(selectedSticker = state.selectedSticker.copy(sticker1 = intent.sticker))
                2 -> state.copy(selectedSticker = state.selectedSticker.copy(sticker2 = intent.sticker))
                else -> state
            }
            state
        }

    fun getLetterSenderReceiver() {
        viewModelScope.launch(Dispatchers.IO) {
            val receiver = createBoxUseCase.getCreatedBox().receiverName
            setState(currentState.copy(title = "${Strings.BOX_ADD_INFO_RECEIVER} $receiver"))
        }
    }

    fun getBoxDesign() {
        viewModelScope.launch {
            getBoxDesignUseCase.getBoxDesignLocal()
                .distinctUntilChanged()
                .filterNotNull()
                .collect { boxDesign ->
                    setState {
                        currentState.copy(
                            boxDesign = boxDesign
                        )
                    }
                }
        }
    }

    private fun clearYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.ClearMusic) -> BoxGuideState =
        { state, _ ->
            createBoxUseCase.youtubeUrl(null)
            state.copy(youtubeUrl = null)
        }

    private fun saveYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.SaveMusic) -> BoxGuideState =
        { state, intent ->
            createBoxUseCase.youtubeUrl(intent.youtubeUrl)
            state.copy(youtubeUrl = intent.youtubeUrl)
        }

    private fun saveLetterBoxGuideState(): suspend (BoxGuideState, BoxGuideIntent.SaveLetter) -> BoxGuideState =
        { state, intent ->
            createBoxUseCase.letterContent(intent.letter.letterContent)
            createBoxUseCase.envelopId(intent.letter.envelope.envelopeId)
            state.copy(letter = intent.letter)
        }

    private fun savePhoto(): suspend (BoxGuideState, BoxGuideIntent.SavePhoto) -> BoxGuideState =
        { state, intent ->
            val photo = Photo(
                photoUrl = intent.imageUri,
                contentDescription = intent.contentDescription
            )
            state.copy(photo = photo)
        }

}