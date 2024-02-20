package com.example.home.profileimage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.home.navigation.SettingsArgs.PROFILE_URL
import com.packy.domain.usecase.profile.GetProfilesUseCase
import com.packy.domain.usecase.profile.UpdateProfileUseCase
import com.packy.lib.utils.filterSuccess
import com.packy.lib.utils.unwrapResource
import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsProfileImageViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val getProfilesUseCase: GetProfilesUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    MviViewModel<SettingsProfileImageIntent, SettingsProfileImageState, SettingsProfileImageEffect>() {
    override fun createInitialState(): SettingsProfileImageState = SettingsProfileImageState(

    )

    override fun handleIntent() {
        subscribeIntent<SettingsProfileImageIntent.OnSaveClick> {
            currentState.currentProfileImage?.id?.toInt()
                ?.let { id ->
                    updateProfileUseCase.updateProfile(id)
                        .filterSuccess()
                        .unwrapResource()
                        .collect{
                            setState {
                                it.copy(
                                    prevProfileImageUrl = currentState.currentProfileImage?.imageUrl
                                )
                            }
                            sendEffect(SettingsProfileImageEffect.MoveToBack)
                        }
                }
        }
        subscribeIntent<SettingsProfileImageIntent.OnBackClick> { sendEffect(SettingsProfileImageEffect.MoveToBack) }
        subscribeStateIntent<SettingsProfileImageIntent.OnChangeProfile> { state, intent ->
            state.copy(
                currentProfileImage = intent.newProfileImage
            )
        }
    }

    init {
        savedStateHandle.get<String>(PROFILE_URL)?.let { profileUrl ->
            viewModelScope.launch {
                setState {
                    it.copy(
                        prevProfileImageUrl = profileUrl,
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
                            currentProfileImage = prfiles.firstOrNull { it.imageUrl == state.prevProfileImageUrl },
                            profiles = prfiles
                        )
                    }
                }

        }
    }
}