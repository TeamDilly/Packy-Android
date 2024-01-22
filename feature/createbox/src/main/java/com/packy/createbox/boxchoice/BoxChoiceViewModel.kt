package com.packy.createbox.boxchoice

import androidx.lifecycle.viewModelScope
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxChoiceViewModel @Inject constructor(
    private val getBoxDesignUseCase: GetBoxDesignUseCase
) :
    MviViewModel<BoxChoiceIntent, BoxChoiceState, BoxChoiceEffect>() {
    override fun createInitialState(): BoxChoiceState = BoxChoiceState(
        selectedBox = null,
        boxDesignList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<BoxChoiceIntent.OnBackClick> { sendEffect(BoxChoiceEffect.MoveToBack) }
        subscribeIntent<BoxChoiceIntent.OnCloseClick> { sendEffect(BoxChoiceEffect.CloseCreateBox) }
        subscribeIntent<BoxChoiceIntent.OnSaveClick> { sendEffect(BoxChoiceEffect.SaveBoxInfo) }
        subscribeStateIntent<BoxChoiceIntent.ChangeSelectBox> { state, intent ->
            state.copy(selectedBox = intent.selectedBox)
        }
    }

    fun getBoxDesign() {
        viewModelScope.launch {
            getBoxDesignUseCase.getBoxDesign()
                .filterSuccess()
                .unwrapResource()
                .collect { boxDesignList ->
                    setState {
                        BoxChoiceState(
                            selectedBox = boxDesignList.firstOrNull(),
                            boxDesignList = boxDesignList
                        )
                    }
                }
        }
    }
}