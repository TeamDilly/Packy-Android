package com.example.home.profileimage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.home.navigation.SettingsArgs.PROFILE_URL
import com.packy.domain.usecase.profile.GetProfilesUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsProfileImageViewModel @Inject constructor(
    private val getProfilesUseCase: GetProfilesUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<SettingsProfileImageIntent, SettingsProfileImageState, SettingsProfileImageEffect>() {
    override fun createInitialState(): SettingsProfileImageState = SettingsProfileImageState(

    )

    override fun handleIntent() {
        subscribeIntent<SettingsProfileImageIntent.OnSaveClick> {  }
        subscribeIntent<SettingsProfileImageIntent.OnBackClick> { sendEffect(SettingsProfileImageEffect.MoveToBack) }
        subscribeStateIntent<SettingsProfileImageIntent.OnChangeProfile>{ state, intent ->
            state.copy(
                currentProfileImageUrl = intent.newProfileImageUrl
            )
        }
    }

    init {
        savedStateHandle.get<String>(PROFILE_URL)?.let{ profileUrl ->
            viewModelScope.launch {
                setState{
                    it.copy(
                        prevProfileImageUrl = profileUrl,
                        currentProfileImageUrl = profileUrl
                    )
                }
            }
        }
    }

    fun initProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            getProfilesUseCase.getProfile()
                .filterSuccess()
                .unwrapResource()
                .collect { prfiles ->
                    setState { state ->
                        state.copy(
                            profiles = prfiles
                        )
                    }
                }

        }
    }
}