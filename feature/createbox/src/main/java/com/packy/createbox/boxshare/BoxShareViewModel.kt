package com.packy.createbox.boxshare

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.packy.common.kakaoshare.KakaoCustomFeed
import com.packy.createbox.navigation.CreateBoxArgs
import com.packy.domain.model.box.BoxDeliverStatus
import com.packy.domain.usecase.box.GetBoxDesignUseCase
import com.packy.domain.usecase.box.UpdateBoxDeliverStatusUseCase
import com.packy.domain.usecase.createbox.CreateBoxUseCase
import com.packy.domain.usecase.createbox.GetKakaoMessageImageUseCase
import com.packy.lib.utils.Resource
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.loadingHandler
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxShareViewModel @Inject constructor(
    private val createBoxUseCase: CreateBoxUseCase,
    private val boxDesignUseCase: GetBoxDesignUseCase,
    private val getKakaoMessageImageUseCase: GetKakaoMessageImageUseCase,
    private val updateBoxDeliverStatusUseCase: UpdateBoxDeliverStatusUseCase,
    private val savedStateHandle: SavedStateHandle,
) :
    MviViewModel<BoxShareIntent, BoxShareState, BoxShareEffect>() {
    override fun createInitialState(): BoxShareState = BoxShareState(
        boxImageUrl = null,
        boxTitle = null,
        receiverName = null,
        shared = false,
        createdBoxId = -1L,
    )

    override fun handleIntent() {
        subscribeIntent<BoxShareIntent.ShareKakao> {
            sharedBox()
        }
        subscribeIntent<BoxShareIntent.OnLazySharClick> {
            sendEffect(BoxShareEffect.ShowLazyShareDialog)
        }
        subscribeIntent<BoxShareIntent.LazyShared> {
            lazyShar()
        }
        subscribeIntent<BoxShareIntent.OnExitClick> {
            sendEffect(BoxShareEffect.MoveToMain)
        }
    }

    private suspend fun lazyShar() {
        updateBoxDeliverStatusUseCase.updateBoxDeliverStatus(
            currentState.createdBoxId,
            BoxDeliverStatus.WAITING,
        ).loadingHandler { setState { state -> state.copy(isLoading = it) } }
            .filterSuccess()
            .unwrapResource()
            .collect {
                sendEffect(BoxShareEffect.MoveToMain)
            }
    }

    init {
        val createBoxId = savedStateHandle.get<Long>(CreateBoxArgs.CREATED_BOX_ID)
            ?: throw IllegalArgumentException("createBoxId is null")
        viewModelScope.launch {
            setState {
                it.copy(
                    createdBoxId = createBoxId
                )
            }
        }
    }

    private suspend fun sharedBox() {
        val giftBoxId = currentState.createdBoxId
        getKakaoMessageImageUseCase.getKakaoMessageImage(giftBoxId)
            .loadingHandler { setState { state -> state.copy(isLoading = it) } }
            .filterSuccess()
            .unwrapResource()
            .take(1)
            .collect { kakaoMessageImage ->
                val kakaoCustomFeed = KakaoCustomFeed(
                    sender = createBoxUseCase.getCreatedBox().senderName ?: "",
                    receiver = createBoxUseCase.getCreatedBox().receiverName ?: "",
                    imageUrl = kakaoMessageImage,
                    boxId = currentState.createdBoxId
                )
                sendEffect(BoxShareEffect.KakaoShare(kakaoCustomFeed))
            }
    }

    suspend fun initState() {
        val boxImageUrl = boxDesignUseCase.getBoxDesignLocal().firstOrNull()?.boxNormal
        val createBox = createBoxUseCase.getCreatedBox()
        setState(
            currentState.copy(
                boxImageUrl = createBox.boxImage ?: boxImageUrl,
                boxTitle = createBox.name,
                receiverName = createBox.receiverName
            )
        )
    }

    fun kakaoShare(shared: Resource<String?>) {
        when (shared) {
            is Resource.Success -> {
                viewModelScope.launch {
                    updateBoxDeliverStatusUseCase.updateBoxDeliverStatus(
                        currentState.createdBoxId,
                        BoxDeliverStatus.DELIVERED
                    ).loadingHandler { setState { state -> state.copy(isLoading = it) } }
                        .filterSuccess()
                        .unwrapResource()
                        .collect {
                            setState(currentState.copy(shared = true))
                        }
                }
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