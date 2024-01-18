package com.packy.createbox.createboax.addpackymusic

import com.packy.core.widget.youtube.YoutubeState
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxPackyMusicViewModel @Inject constructor() :
    MviViewModel<CreateBoxPackyMusicIntent, CreateBoxPackyMusicState, CreateBoxPackyMusicEffect>() {
    override fun createInitialState(): CreateBoxPackyMusicState = CreateBoxPackyMusicState(
        currentTitle = "",
        currentHashTag = emptyList(),
        currentMusicIndex = 0,
        musicState = YoutubeState.INIT
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
            state.copy(musicState = intent.state)
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
                thumbnail = "https://i.ytimg.com/vi/gqmtefROI_0/maxresdefault.jpg"
            ),
            PackyMusic(
                title = "Butter",
                hashTag = listOf("#BTS", "#Butter"),
                videoId = "WMweEpGlu_U",
                thumbnail = "https://i.ytimg.com/vi/WMweEpGlu_U/maxresdefault.jpg"
            ),
            PackyMusic(
                title = "Permission to Dance",
                hashTag = listOf("#BTS", "#PermissionToDance"),
                videoId = "CuklIb9d3fI",
                thumbnail = "https://i.ytimg.com/vi/CuklIb9d3fI/maxresdefault.jpg"
            ),
            PackyMusic(
                title = "Life Goes On",
                hashTag = listOf("#BTS", "#LifeGoesOn"),
                videoId = "2CGPWmTqYkU",
                thumbnail = "https://i.ytimg.com/vi/2CGPWmTqYkU/maxresdefault.jpg"
            ),
        )
    }
}