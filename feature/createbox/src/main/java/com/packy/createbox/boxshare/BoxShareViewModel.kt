package com.packy.createbox.boxshare

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.createbox.boxtitle.BoxAddTitleIntent
import com.packy.createbox.navigation.CreateBoxArgs
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.lib.utils.Resource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxShareViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val boxDesignUseCase: GetBoxDesignUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<BoxShareIntent, BoxShareState, BoxShareEffect>() {
    override fun createInitialState(): BoxShareState = BoxShareState(
        boxImageUrl = null,
        boxTitle = null,
        receiverName = null,
        shared = false,
        createdBox = null,
    )

    override fun handleIntent() {
        subscribeIntent<BoxShareIntent.ShareKakao>(sharedBox())
        subscribeIntent<BoxShareIntent.OnBackClick> { sendEffect(BoxShareEffect.MoveToBack) }
        subscribeIntent<BoxShareIntent.OnCloseClick> {
            // TODO:: 나가기 나중에 만들기 필요
        }
    }

    init {
        savedStateHandle.get<String>(CreateBoxArgs.CREATED_BOX_ID)?.let { createBoxId ->
            viewModelScope.launch {
                setState {
                    it.copy(
                        createdBox = createBoxId
                    )
                }
            }
        }
    }

    private fun sharedBox(): suspend (BoxShareIntent.ShareKakao) -> Unit =
        {
            val boxDesign = boxDesignUseCase.getBoxDesignLocal().first()
            val kakaoCustomFeed = KakaoCustomFeed(
                sender = createBoxUseCase.getCreatedBox().senderName ?: "",
                receiver = createBoxUseCase.getCreatedBox().receiverName ?: "",
                imageUrl = boxDesign?.boxNormal ?: "",
                boxId = currentState.createdBox ?: ""
            )
            sendEffect(BoxShareEffect.KakaoShare(kakaoCustomFeed))
        }

    suspend fun initState() {
        val boxImageUrl = boxDesignUseCase.getBoxDesignLocal().firstOrNull()?.boxNormal
        val createBox = createBoxUseCase.getCreatedBox()
        setState(
            currentState.copy(
                boxImageUrl = boxImageUrl,
                boxTitle = createBox.name,
                receiverName = createBox.receiverName
            )
        )
    }

    fun kakaoShare(shared: Resource<String?>) {
        when (shared) {
            is Resource.Success -> {
                setState(currentState.copy(shared = true))
            }

            is Resource.NetworkError,
            is Resource.ApiError,
            is Resource.Loading,
            is Resource.NullResult -> {
                setState(currentState.copy(shared = false))
            }
        }
    }
}