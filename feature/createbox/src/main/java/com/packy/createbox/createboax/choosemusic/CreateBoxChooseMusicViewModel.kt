package com.packy.createbox.createboax.choosemusic

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateBoxChooseMusicViewModel @Inject constructor() :
    MviViewModel<CreateBoxChooseMusicIntent, CreateBoxChooseMusicState, CreateBoxChooseMusicEffect>() {
    override fun createInitialState(): CreateBoxChooseMusicState = CreateBoxChooseMusicState
    override fun handleIntent() {
        subscribeIntent<CreateBoxChooseMusicIntent.OnChooseYourMusicClick> {
            sendEffect(CreateBoxChooseMusicEffect.MoveToYourMusic)
        }
        subscribeIntent<CreateBoxChooseMusicIntent.OnPackyMusicClick> {
            sendEffect(CreateBoxChooseMusicEffect.MoveToPackyMusic)
        }
        subscribeIntent<CreateBoxChooseMusicIntent.OnCloseClick> {
            sendEffect(CreateBoxChooseMusicEffect.CloseBottomSheet)
        }
    }
}