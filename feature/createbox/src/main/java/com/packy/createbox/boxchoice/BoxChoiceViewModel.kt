package com.packy.createbox.boxchoice

import androidx.lifecycle.viewModelScope
import com.packy.createbox.common.boxDesign
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxFlagUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxChoiceViewModel @Inject constructor(
    private val getBoxDesignUseCase: GetBoxDesignUseCase,
    private val createBoxFlagUseCase: CreateBoxFlagUseCase,
    private val createBoxUseCase: CreateBoxUseCase
) :
    MviViewModel<BoxChoiceIntent, BoxChoiceState, BoxChoiceEffect>() {
    override fun createInitialState(): BoxChoiceState = BoxChoiceState(
        selectedBox = null,
        boxDesignList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<BoxChoiceIntent.OnBackClick> { sendEffect(BoxChoiceEffect.MoveToBack) }
        subscribeIntent<BoxChoiceIntent.OnCloseClick> { sendEffect(BoxChoiceEffect.CloseCreateBox) }
        subscribeIntent<BoxChoiceIntent.OnSaveClick> {
            currentState.selectedBox?.let { boxDesign ->
                setBoxDesign(boxDesign)
                createBoxFlagUseCase.shouldShowBoxMotion()
                    .take(1)
                    .map { shouldShowBoxMotion ->
                        createBoxFlagUseCase.shownShowBoxMotion()
                        BoxChoiceEffect.SaveBoxInfo(
                            shouldShowBoxMotion,
                            currentState.selectedBox
                        )
                    }
                    .collect { effect ->
                        sendEffect(effect)
                    }
            }
            // FIXME: 정상적인 동작이라면 여기 들어올일은 없음. 대비를 위해 무언가 추가해두자.
        }
        subscribeStateIntent<BoxChoiceIntent.ChangeSelectBox> { state, intent ->
            state.copy(selectedBox = intent.selectedBox)
        }
    }

    fun getBoxDesign() {
        viewModelScope.launch {
            getBoxDesignUseCase.getBoxDesign()
                .filterSuccess()
                .unwrapResource()
                .zip(flowOf(createBoxUseCase.getCreatedBox())) { boxDesignList, createdBox ->
                    boxDesignList to createdBox
                }
                .collect { (boxDesignList, createdBox) ->
                    val selectedBox = boxDesignList.firstOrNull { it.id == createdBox.boxId }
                        ?: boxDesignList.firstOrNull()
                    setState {
                        BoxChoiceState(
                            selectedBox = selectedBox,
                            boxDesignList = boxDesignList
                        )
                    }
                }
        }
    }

    private fun setBoxDesign(boxDesign: BoxDesign) {
        viewModelScope.launch {
            getBoxDesignUseCase.setBoxDesignLocal(boxDesign)
            createBoxUseCase.boxDesign(boxDesign.id, boxDesign.boxNormal)
        }
    }
}