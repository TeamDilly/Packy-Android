package com.packy.createbox.boxguide

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor() :
    MviViewModel<BoxGuideIntent, BoxGuideState, BoxGuideEffect>() {
    override fun createInitialState(): BoxGuideState = BoxGuideState(
        photo = null,
        Letter = null,
        youtubeUrl = null,
        sticker1 = null,
        sticker2 = null,
    )

    override fun handleIntent() {
        subscribeIntent<BoxGuideIntent.OnBackClick> { sendEffect(BoxGuideEffect.MoveToBack) }
        subscribeIntent<BoxGuideIntent.OnSaveClick> { sendEffect(BoxGuideEffect.SaveBox) }
        subscribeIntent<BoxGuideIntent.ShowBottomSheet> { sendEffect(BoxGuideEffect.ShowBottomSheet(it.boxGuideBottomSheetRoute)) }
        subscribeStateIntent<BoxGuideIntent.SavePhoto>(savePhoto())
        subscribeStateIntent<BoxGuideIntent.SaveLetter>(saveLetterBoxGuideStateSuspendFunction2())
        subscribeStateIntent<BoxGuideIntent.SaveMusic>(saveYoutubeMusic())
        subscribeStateIntent<BoxGuideIntent.ClearMusic>(clearYoutubeMusic())
    }

    private fun clearYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.ClearMusic) -> BoxGuideState =
        { state, _ ->
            state.copy(youtubeUrl = null)
        }

    private fun saveYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.SaveMusic) -> BoxGuideState =
        { state, intent ->
            state.copy(youtubeUrl = intent.youtubeUrl)
        }

    private fun saveLetterBoxGuideStateSuspendFunction2(): suspend (BoxGuideState, BoxGuideIntent.SaveLetter) -> BoxGuideState =
        { state, intent ->
            state.copy(Letter = intent.Letter)
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