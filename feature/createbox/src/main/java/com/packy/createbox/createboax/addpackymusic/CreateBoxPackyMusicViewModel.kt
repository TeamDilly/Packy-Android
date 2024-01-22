package com.packy.createbox.createboax.addpackymusic

import androidx.lifecycle.viewModelScope
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.usecase.music.SuggestionMusicUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.map
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxPackyMusicViewModel @Inject constructor(
    private val suggestionMusicUseCase: SuggestionMusicUseCase
) :
    MviViewModel<CreateBoxPackyMusicIntent, CreateBoxPackyMusicState, CreateBoxPackyMusicEffect>() {

    override fun createInitialState(): CreateBoxPackyMusicState = CreateBoxPackyMusicState(
        currentMusicIndex = 0,
        music = emptyList()
    )

    override fun handleIntent() {
        subscribeIntent<CreateBoxPackyMusicIntent.OnBackClick> {
            sendEffect(CreateBoxPackyMusicEffect.MoveToBack)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxPackyMusicEffect.CloseBottomSheet)
        }
        subscribeIntent<CreateBoxPackyMusicIntent.OnSaveClick> {

        }

        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusicState> { state, intent ->
            val newMusicList = state.music.toMutableList().apply {
                this.replaceAll { music ->
                    music.copy(state = YoutubeState.PAUSED)
                }
                this[intent.index] = this[intent.index].copy(state = intent.state)
            }
            state.copy(music = newMusicList)
        }
        subscribeStateIntent<CreateBoxPackyMusicIntent.ChangeMusic> { state, intent ->
            state.copy(currentMusicIndex = intent.index)
        }
    }

    fun getSuggestionMusic() {
        viewModelScope.launch {
            val packyMusicList = suggestionMusicUseCase.suggestionMusic()
                .filterSuccess()
                .unwrapResource()
                .single()
                .map { music ->
                    PackyMusic(
                        title = music.title,
                        hashTag = music.hashtags,
                        videoId = music.videoId,
                        state = YoutubeState.INIT
                    )
                }
            setState {
                it.copy(music = packyMusicList)
            }
        }
    }
}