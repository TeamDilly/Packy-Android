package com.packy.createbox.boxshare

import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class BoxShareViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val boxDesignUseCase: GetBoxDesignUseCase,
) :
    MviViewModel<BoxShareIntent, BoxShareState, BoxShareEffect>() {
    override fun createInitialState(): BoxShareState = BoxShareState(
        boxImageUrl = null,
        boxTitle = null
    )

    override fun handleIntent() {
        subscribeIntent<BoxShareIntent.ShareKakao>(createBox())
    }

    private fun createBox(): suspend (BoxShareIntent.ShareKakao) -> Unit =
        {
            val createBox = createBoxUseCase.getCreatedBox()

            val box = createBoxUseCase.createBox(createBox)
            if (box is Resource.Success) {
                val boxDesign = boxDesignUseCase.getBoxDesignLocal().first()
                val kakaoCustomFeed = KakaoCustomFeed(
                    sender = createBox.senderName ?: "",
                    receiver = createBox.receiverName ?: "",
                    imageUrl = boxDesign?.boxFull ?: "",
                    boxId = box.data.id
                )
                sendEffect(BoxShareEffect.KakaoShare(kakaoCustomFeed))
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

    fun kakaoShare(shared: Resource<String?>) {
        when (shared) {
            is Resource.Success -> sendEffect(BoxShareEffect.SuccessShare)
            is Resource.NetworkError,
            is Resource.ApiError,
            is Resource.Loading,
            is Resource.NullResult -> sendEffect(BoxShareEffect.FailedShare)
        }
    }
}