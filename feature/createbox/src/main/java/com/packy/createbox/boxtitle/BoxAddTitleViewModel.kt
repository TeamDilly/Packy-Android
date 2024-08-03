package com.packy.createbox.boxtitle

import androidx.lifecycle.viewModelScope
import com.packy.createbox.common.name
import com.packy.domain.usecase.createbox.CreateBoxFlagUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxAddTitleViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val createBoxFlagUseCase: CreateBoxFlagUseCase,

    ) :
    MviViewModel<BoxAddTitleIntent, BoxAddTitleState, BoxAddTitleEffect>() {
    override fun createInitialState(): BoxAddTitleState = BoxAddTitleState(
        boxTitle = ""
    )

    override fun handleIntent() {
        subscribeIntent<BoxAddTitleIntent.OnBackClick> {
            sendEffect(BoxAddTitleEffect.MoveToBack)
        }
        subscribeStateIntent<BoxAddTitleIntent.OnTitleChange> { state, intent ->
            createBoxUseCase.name(currentState.boxTitle)
            val boxAllReady = createBoxUseCase.getCreatedBox().boxAllReady()

            state.copy(
                boxTitle = intent.text,
                boxAllReady = boxAllReady
            )
        }
        subscribeIntent<BoxAddTitleIntent.MoveToNext> {
            createBoxUseCase.name(currentState.boxTitle)
            createBox()
        }
    }

    fun initBoxTitle() {
        viewModelScope.launch {
            val createBox = createBoxUseCase.getCreatedBox()
            val name = createBox.name
            val boxAllReady = createBox.boxAllReady()

            setState(
                currentState.copy(
                    boxTitle = name ?: "",
                    boxAllReady = boxAllReady
                )
            )
        }
    }

    private suspend fun createBox() {
        val createBox = createBoxUseCase.getCreatedBox()
        setState {
            it.copy(isLoading = true)
        }
        val box = createBoxUseCase.createBox(createBox)
        if (box is Resource.Success) {
            val showMotion = createBoxFlagUseCase.shouldShowBoxSharMotion().firstOrNull() ?: false
            createBoxFlagUseCase.shownShowBoxSharMotion()
            sendEffect(
                BoxAddTitleEffect.MoveToShared(
                    motionBoxId = createBoxUseCase.getCreatedBox().boxId ?: 0,
                    createBoxId = box.data.id,
                    showMotion = showMotion,
                    lottieAnimation = createBox.lottieAnimation
                )
            )
        } else {
            sendEffect(BoxAddTitleEffect.FailCreateBox(box.message))
        }
    }
}