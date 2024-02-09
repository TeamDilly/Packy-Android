package com.packy.createbox.createboax.boxchange

import androidx.lifecycle.viewModelScope
import com.packy.createbox.boxchoice.BoxChoiceState
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxChangeViewModel @Inject constructor(
    private val getBoxDesignUseCase: GetBoxDesignUseCase,
) :
    MviViewModel<CreateBoxChangeIntent, CreateBoxChangeState, CreateBoxChangeEffect>() {
    override fun createInitialState(): CreateBoxChangeState = CreateBoxChangeState(
        currentBox = null,
        boxDesignList = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxChangeIntent.OnConfirmClick> {
            sendEffect(CreateBoxChangeEffect.OnConfirmClick)
        }
        subscribeStateIntent<CreateBoxChangeIntent.ChangeBox> { state, intent ->
            sendEffect(CreateBoxChangeEffect.ChangeBox(intent.box))
            state.copy(currentBox = intent.box)
        }
    }

    fun getBoxDesign(currentBox: BoxDesign) {
        viewModelScope.launch {
            getBoxDesignUseCase.getBoxDesign()
                .filterSuccess()
                .unwrapResource()
                .collect { boxDesignList ->
                    setState {
                        currentState.copy(
                            currentBox = currentBox,
                            boxDesignList = boxDesignList
                        )
                    }
                }
        }
    }
}