package com.packy.createbox.createboax.addpackymusic

import androidx.lifecycle.viewModelScope
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.usecase.music.SuggestionMusicUseCase
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoxPackyMusicViewModel @Inject constructor(
    private val suggestionMusicUseCase: SuggestionMusicUseCase
) :
    MviViewModel<CreateBoxPackyMusicIntent, CreateBoxPackyMusicState, CreateBoxPackyMusicEffect>() {
        init {
            viewModelScope.launch {
                suggestionMusicUseCase.suggestionMusic()
                    .collect{
                        println("LOGEE resource $it")
                    }
            }
        }
    override fun createInitialState(): CreateBoxPackyMusicState = CreateBoxPackyMusicState(
        currentMusicIndex = 0,
        music = dumyMusic
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

    companion object {
        val dumyMusic: List<PackyMusic> = listOf(
            PackyMusic(
                title = "Dynamite",
                hashTag = listOf("#BTS", "#Dynamite"),
                videoId = "gdZLi9oWNZg",
                thumbnail = "https://i.ytimg.com/vi/gqmtefROI_0/maxresdefault.jpg",
                state = YoutubeState.INIT
            ),
            PackyMusic(
                title = "Butter",
                hashTag = listOf("#BTS", "#Butter"),
                videoId = "WMweEpGlu_U",
                thumbnail = "https://i.ytimg.com/vi/WMweEpGlu_U/maxresdefault.jpg",
                state = YoutubeState.INIT
            ),
            PackyMusic(
                title = "Permission to Dance",
                hashTag = listOf("#BTS", "#PermissionToDance"),
                videoId = "CuklIb9d3fI",
                thumbnail = "https://i.ytimg.com/vi/CuklIb9d3fI/maxresdefault.jpg",
                state = YoutubeState.INIT
            ),
            PackyMusic(
                title = "Life Goes On",
                hashTag = listOf("#BTS", "#LifeGoesOn"),
                videoId = "2CGPWmTqYkU",
                thumbnail = "https://i.ytimg.com/vi/2CGPWmTqYkU/maxresdefault.jpg",
                state = YoutubeState.INIT
            ),
        )
    }
}