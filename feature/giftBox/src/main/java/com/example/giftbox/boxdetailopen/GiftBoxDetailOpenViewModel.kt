package com.example.giftbox.boxdetailopen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.giftbox.navigation.GiftBoxArgs
import com.example.giftbox.navigation.GiftBoxScreens
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.AnalyticsEvent
import com.packy.core.analytics.FirebaseAnalyticsWrapper
import com.packy.core.analytics.toBundle
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.domain.model.createbox.box.Gift
import com.packy.domain.model.createbox.box.Photo
import com.packy.domain.model.createbox.box.Stickers
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiftBoxDetailOpenViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val getBoxDesignUseCase: GetBoxDesignUseCase,
    savedStateHandle: SavedStateHandle,
) :
    MviViewModel<GiftBoxDetailOpenIntent, GiftBoxDetailOpenState, GiftBoxDetailOpenEffect>() {
    override fun createInitialState(): GiftBoxDetailOpenState = GiftBoxDetailOpenState(
        giftBox = null
    )

    init {
        viewModelScope.launch {
            val giftBox = GiftBoxArgs.getGiftBoxArg(savedStateHandle)
            FirebaseAnalyticsWrapper.logEvent(
                label = AnalyticsConstant.AnalyticsLabel.VIEW,
                bundle = arrayOf<AnalyticsEvent>(
                    AnalyticsConstant.PageName.BOX_DETAIL_OPEN,
                    AnalyticsConstant.ContentId(
                        giftBox?.id.toString()
                    )
                ).toBundle()
            )

            val photo = giftBox?.photos?.first() ?: return@launch
            setState {
                GiftBoxDetailOpenState(
                    giftBox
                )
            }
            savedStateHandle.get<Boolean>(GiftBoxArgs.GIFT_BOX_SHOULD_SHOW_SHARED_ARG)
                ?.let { shouldShow ->

                    setState {
                        it.copy(
                            shouldShowShared = shouldShow
                        )
                    }

                    if (shouldShow) {
                        createBoxUseCase.setCreateBox(
                            CreateBox(
                                boxId = giftBox.box.id,
                                envelopeId = -1,
                                envelopeUrl = giftBox.envelope.imgUrl,
                                gift = giftBox.gift?.type?.let {
                                    Gift(
                                        type = it,
                                        url = giftBox.gift?.url
                                    )
                                },
                                letterContent = giftBox.letterContent,
                                name = giftBox.name,
                                photo = Photo(
                                    description = photo.description,
                                    photoUrl = photo.photoUrl,
                                    sequence = 1
                                ),
                                receiverName = giftBox.receiverName,
                                senderName = giftBox.senderName,
                                stickers = listOf(
                                    Stickers(
                                        id = 1,
                                        imageUri = giftBox.stickers[0].imgUrl,
                                        location = giftBox.stickers[0].location
                                    ),
                                    Stickers(
                                        id = 2,
                                        imageUri = giftBox.stickers[1].imgUrl,
                                        location = giftBox.stickers[1].location
                                    ),
                                ),
                                youtubeUrl = giftBox.youtubeUrl,
                                boxImage = giftBox.box.boxNormal,
                                lottieAnimation = giftBox.box.boxLottie
                            )
                        )
                    }
                }
        }
    }

    override fun handleIntent() {
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnPhotoClick>(showPhoto())
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnLetterClick>(showLetter())
        subscribeStateIntent<GiftBoxDetailOpenIntent.OnGiftClick>(showGift())
        subscribeStateIntent<GiftBoxDetailOpenIntent.CloseDialog> { state, _ ->
            state.copy(
                showDetail = ShowDetail.NONE
            )
        }
        subscribeIntent<GiftBoxDetailOpenIntent.OnBackClick> { sendEffect(GiftBoxDetailOpenEffect.MoveToBack) }
        subscribeIntent<GiftBoxDetailOpenIntent.OnCloseClick> { sendEffect(GiftBoxDetailOpenEffect.GiftBoxClose) }
        subscribeIntent<GiftBoxDetailOpenIntent.BoxShared> {
            currentState.giftBox?.id?.let { giftBoxId -> sendEffect(GiftBoxDetailOpenEffect.MoveToShared(giftBoxId)) }
        }
    }

    private fun showGift(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnGiftClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val gift = currentState.giftBox?.gift
            state.copy(
                showDetail = if (gift != null) ShowDetail.GIFT else ShowDetail.NONE
            )
        }

    private fun showLetter(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnLetterClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val envelope = currentState.giftBox?.envelope
            val letterContent = currentState.giftBox?.letterContent
            if (envelope != null && letterContent != null)
                state.copy(
                    showDetail = ShowDetail.LETTER
                )
            else state
        }

    private fun showPhoto(): suspend (GiftBoxDetailOpenState, GiftBoxDetailOpenIntent.OnPhotoClick) -> GiftBoxDetailOpenState =
        { state, _ ->
            val photo = currentState.giftBox?.photos?.firstOrNull()
            if (photo != null) state.copy(
                showDetail = ShowDetail.PHOTO
            )
            else state
        }
}