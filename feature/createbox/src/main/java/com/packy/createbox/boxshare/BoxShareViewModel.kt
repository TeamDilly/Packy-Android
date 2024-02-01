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
        boxImageUrl = null,
        boxTitle = null
    )

    override fun handleIntent() {
        subscribeIntent<BoxShareIntent.ShareKakao> {

        }
    }

    suspend fun initState() {
        val boxImageUrl = boxDesignUseCase.getBoxDesignLocal().firstOrNull()?.boxFull
        val boxTitle = createBoxUseCase.getCreatedBox().name
        setState(
            currentState.copy(
                boxImageUrl = boxImageUrl,
                boxTitle = boxTitle
            )
        )
    }
}