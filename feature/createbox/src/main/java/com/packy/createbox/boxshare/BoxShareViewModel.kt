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

            // FIXME : 카카오 전달하기 추각 필요.

            val createBox = createBoxUseCase.getCreatedBox()
            createBoxUseCase.createBox(createBox)
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