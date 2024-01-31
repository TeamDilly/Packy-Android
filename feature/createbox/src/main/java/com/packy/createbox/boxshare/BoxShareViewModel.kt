package com.packy.createbox.boxshare

import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class BoxShareViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val boxDesignUseCase: GetBoxDesignUseCase
) :
    MviViewModel<BoxShareIntent, BoxShareState, BoxShareEffect>() {
    override fun createInitialState(): BoxShareState = BoxShareState(
        boxImageUrl = null
    )

    override fun handleIntent() {

    }

    suspend fun initState(){
        val boxImageUrl = boxDesignUseCase.getBoxDesignLocal().firstOrNull()?.boxFull
        setState(currentState.copy(boxImageUrl))
    }
}