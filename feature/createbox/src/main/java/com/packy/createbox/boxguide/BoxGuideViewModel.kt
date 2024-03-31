package com.packy.createbox.boxguide

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.AnalyticsEvent
import com.packy.core.analytics.FirebaseAnalyticsWrapper
import com.packy.core.analytics.toBundle
import com.packy.core.values.Strings
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.common.boxDesign
import com.packy.createbox.common.envelop
import com.packy.createbox.common.gift
import com.packy.createbox.common.letterContent
import com.packy.createbox.common.photo
import com.packy.createbox.common.sticker
import com.packy.createbox.common.youtubeUrl
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.domain.model.createbox.box.Gift
import com.packy.domain.model.createbox.box.Stickers
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxFlagUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.domain.usecase.createbox.GetStickerUseCase
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.domain.usecase.photo.UploadImageUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor(
    private val getBoxDesignUseCase: GetBoxDesignUseCase,
    private val createBoxUseCase: CreateBoxUseCase,
    private val createBoxFlagUseCase: CreateBoxFlagUseCase,
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
        subscribeIntent<BoxGuideIntent.OnTutorialClick> {
            createBoxFlagUseCase.shownShowBoxTutorial()
            setState(currentState.copy(showTutorial = false))
        }
    }

    private suspend fun createBox(boxGuideIntent: BoxGuideIntent.OnSaveClick) {
        sendEffect(BoxGuideEffect.SaveBox)
    }

    private fun saveBox(): suspend (BoxGuideState, BoxGuideIntent.SaveBox) -> BoxGuideState =
        { state, intent ->
            getBoxDesignUseCase.setBoxDesignLocal(intent.boxDesign)
            createBoxUseCase.boxDesign(
                intent.boxDesign.id,
                intent.boxDesign.boxNormal
            )
            state.copy(boxDesign = intent.boxDesign)
        }

    private fun saveGift(): suspend (BoxGuideState, BoxGuideIntent.SaveGift) -> BoxGuideState =
        { state, intent ->
            createBoxUseCase.gift(
                Gift(
                    type = "photo",
                    url = intent.imageUri?.toString()
                )
            )
            state.copy(gift = intent.imageUri)
        }

    private fun saveSticker(): suspend (BoxGuideState, BoxGuideIntent.SaveSticker) -> BoxGuideState =
        { state, intent ->
            val selectedSticker = intent.selectedSticker
            createBoxUseCase.sticker(
                id = selectedSticker.get(1)?.id,
                location = 1,
                imageUri = selectedSticker.get(1)?.imgUrl
            )
            createBoxUseCase.sticker(
                id = selectedSticker.get(2)?.id,
                location = 2,
                imageUri = selectedSticker.get(2)?.imgUrl
            )
            state.copy(selectedSticker = selectedSticker)
        }

    suspend fun initUiState() {
        val createBox = createBoxUseCase.getCreatedBox()
        val receiver = createBox.receiverName
        val photo = createBox.photo?.photoUrl?.let {
            Photo(
                photoUrl = Uri.parse(it),
                contentDescription = createBox.photo?.description ?: ""
            )
        }
        val createBoxLetterContent = createBox.letterContent
        val createBoxEnvelopeId = createBox.envelopeId
        val createBoxEnvelopeUrl = createBox.envelopeUrl

        val letter =
            if (createBoxLetterContent != null && createBoxEnvelopeId != null && createBoxEnvelopeUrl != null)
                Letter(
                    letterContent = createBoxLetterContent,
                    envelope = Envelope(
                        envelopeId = createBoxEnvelopeId,
                        envelopeUrl = createBoxEnvelopeUrl
                    )
                ) else null

        val boxDesign = getBoxDesignUseCase.getBoxDesignLocal()
            .distinctUntilChanged()
            .filterNotNull()
            .firstOrNull()

        val sticker1 = createBox.stickers.firstOrNull { it.location == 1 }.let {
            val id = it?.id
            val imageUri = it?.imageUri
            if (it == null) return@let null
            if (id == null || imageUri == null) return@let null
            Sticker(
                id = id,
                imgUrl = imageUri
            )
        }

        val sticker2 = createBox.stickers.firstOrNull { it.location == 2 }.let {
            val id = it?.id
            val imageUri = it?.imageUri
            if (it == null) return@let null
            if (id == null || imageUri == null) return@let null
            Sticker(
                id = id,
                imgUrl = imageUri
            )
        }

        val showTutorial = createBoxFlagUseCase.shouldShowBoxTutorial().firstOrNull() ?: false

        setState(
            currentState.copy(
                title = "${Strings.BOX_ADD_INFO_RECEIVER} $receiver",
                photo = photo,
                letter = letter,
                youtubeUrl = createBox.youtubeUrl,
                selectedSticker = SelectedSticker(
                    sticker1 = sticker1,
                    sticker2 = sticker2
                ),
                gift = createBox.gift?.url?.let { Uri.parse(it) },
                boxDesign = boxDesign,
                showTutorial = showTutorial
            )
        )
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
            createBoxUseCase.envelop(
                intent.letter.envelope.envelopeId,
                intent.letter.envelope.envelopeUrl
            )
            state.copy(letter = intent.letter)
        }

    private fun savePhoto(): suspend (BoxGuideState, BoxGuideIntent.SavePhoto) -> BoxGuideState =
        { state, intent ->
            val savePhoto = com.packy.domain.model.createbox.box.Photo(
                description = intent.contentDescription,
                photoUrl = intent.imageUri.toString(),
                sequence = 1
            )
            createBoxUseCase.photo(savePhoto)

            val photo = Photo(
                photoUrl = intent.imageUri,
                contentDescription = intent.contentDescription
            )
            state.copy(photo = photo)
        }

    fun musicStop() {
        setState(
            currentState.copy(youtubeState = YoutubeState.PAUSED)
        )
    }

    fun completeBoxLog() {
        viewModelScope.launch {
            val emptyItems = buildList<AnalyticsConstant.EmptyItem> {
                val createBox = createBoxUseCase.getCreatedBox()
                if (createBox.gift == null) {
                    add(AnalyticsConstant.EmptyItem.GIFT)
                }
                if (createBox.photo == null) {
                    add(AnalyticsConstant.EmptyItem.PHOTO)
                }
                if (createBox.stickers.find { it.location == 1 }?.imageUri == null) {
                    add(AnalyticsConstant.EmptyItem.STICKER1)
                }
                if (createBox.stickers.find { it.location == 2 }?.imageUri == null) {
                    add(AnalyticsConstant.EmptyItem.STICKER2)
                }
                if (createBox.letterContent == null) {
                    add(AnalyticsConstant.EmptyItem.LETTER)
                }
                if (createBox.youtubeUrl == null) {
                    add(AnalyticsConstant.EmptyItem.MUSIC)
                }
            }
            FirebaseAnalyticsWrapper.logEvent(
                label = AnalyticsConstant.AnalyticsLabel.CLICK,
                bundle = arrayOf<AnalyticsEvent>(
                    AnalyticsConstant.PageName.BOX_DETAIL,
                    AnalyticsConstant.ComponentName.BOX_DETAIL_DONE_BUTTON,
                    AnalyticsConstant.EmptyItems(emptyItems)
                ).toBundle()
            )
        }
    }
}