package com.example.home.archive

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.AnalyticsEvent
import com.packy.core.analytics.FirebaseAnalyticsWrapper
import com.packy.core.analytics.toBundle
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.usecase.archive.GetArchiveGift
import com.packy.domain.usecase.archive.GetArchiveLetter
import com.packy.domain.usecase.archive.GetArchiveMusic
import com.packy.domain.usecase.archive.GetArchivePhoto
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    private val getArchiveMusic: GetArchiveMusic,
    private val getArchivePhoto: GetArchivePhoto,
    private val getArchiveGift: GetArchiveGift,
    private val getArchiveLetter: GetArchiveLetter
) :
    MviViewModel<ArchiveIntent, ArchiveState, ArchiveEffect>() {
    override fun createInitialState(): ArchiveState = ArchiveState(
        showArchiveType = ShowArchiveType.PHOTO,
        photos = PagingData.empty(),
        musics = PagingData.empty(),
        letter = PagingData.empty(),
        gifts = PagingData.empty(),
    )

    override fun handleIntent() {
        subscribeStateIntent<ArchiveIntent.OnArchiveTypeClick> { state, intent ->
            if (state.showArchiveType == intent.type) {
                return@subscribeStateIntent state
            }
            FirebaseAnalyticsWrapper.logEvent(
                label = AnalyticsConstant.AnalyticsLabel.VIEW,
                bundle = arrayOf<AnalyticsEvent>(
                    AnalyticsConstant.PageName.ARCHIVE,
                    when (intent.type) {
                        ShowArchiveType.PHOTO -> AnalyticsConstant.ComponentName.PHOTO
                        ShowArchiveType.LETTER -> AnalyticsConstant.ComponentName.LETTER
                        ShowArchiveType.MUSIC -> AnalyticsConstant.ComponentName.MUSIC
                        ShowArchiveType.GIFT -> AnalyticsConstant.ComponentName.GIFT
                    }
                ).toBundle()
            )
            state.copy(showArchiveType = intent.type)
        }
        subscribeStateIntent<ArchiveIntent.OnArchiveClick> { state, intent ->
            state.copy(showArchive = intent.showArchive)
        }
        subscribeStateIntent<ArchiveIntent.CloseArchive> { state, _ ->
            state.copy(showArchive = ShowArchive.NonType)
        }
    }

    fun listInit() {
        getPhotos()
        getMusics()
        getGifts()
        getLetters()
    }

    private fun getMusics() {
        viewModelScope.launch(Dispatchers.IO) {
            getArchiveMusic.getArchiveMusic()
                .cachedIn(viewModelScope)
                .collect { musics ->
                    setState {
                        it.copy(
                            musics = musics
                        )
                    }
                }
        }
    }

    private fun getPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            setState {
                it.copy(
                    isLoading = true
                )
            }
            getArchivePhoto.getArchivePhoto()
                .cachedIn(viewModelScope)
                .collect { photos ->
                    setState {
                        it.copy(
                            photos = photos,
                            isLoading = false
                        )
                    }
                }
        }
    }

    private fun getGifts() {
        viewModelScope.launch(Dispatchers.IO) {
            getArchiveGift.getArchiveGift()
                .cachedIn(viewModelScope)
                .collect { gifts ->
                    setState {
                        it.copy(
                            gifts = gifts
                        )
                    }
                }
        }
    }

    private fun getLetters() {
        viewModelScope.launch(Dispatchers.IO) {
            getArchiveLetter.getArchiveLetter()
                .cachedIn(viewModelScope)
                .collect { letters ->
                    setState {
                        it.copy(
                            letter = letters
                        )
                    }
                }
        }
    }
}