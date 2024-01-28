package com.packy.createbox.boxguide

import androidx.lifecycle.viewModelScope
import com.packy.core.values.Strings
import com.packy.domain.usecase.letter.GetLetterSenderReceiverUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor(
    private val useCase: GetLetterSenderReceiverUseCase
) :
    MviViewModel<BoxGuideIntent, BoxGuideState, BoxGuideEffect>() {
    override fun createInitialState(): BoxGuideState = BoxGuideState(
        title = "",
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
        subscribeStateIntent<BoxGuideIntent.SaveLetter>(saveLetterBoxGuideState())
        subscribeStateIntent<BoxGuideIntent.SaveMusic>(saveYoutubeMusic())
        subscribeStateIntent<BoxGuideIntent.ClearMusic>(clearYoutubeMusic())
    }

    fun getLetterSenderReceiver() {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.getLetterSenderReceiver()
                .distinctUntilChanged()
                .filterNotNull()
                .collect {
                    setState(currentState.copy(title = "${Strings.BOX_ADD_INFO_RECEIVER} ${it.receiver}"))
                }
        }
    }

    private fun clearYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.ClearMusic) -> BoxGuideState =
        { state, _ ->
            state.copy(youtubeUrl = null)
        }

    private fun saveYoutubeMusic(): suspend (BoxGuideState, BoxGuideIntent.SaveMusic) -> BoxGuideState =
        { state, intent ->
            state.copy(youtubeUrl = intent.youtubeUrl)
        }

    private fun saveLetterBoxGuideState(): suspend (BoxGuideState, BoxGuideIntent.SaveLetter) -> BoxGuideState =
        { state, intent ->
            state.copy(Letter = intent.letter)
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