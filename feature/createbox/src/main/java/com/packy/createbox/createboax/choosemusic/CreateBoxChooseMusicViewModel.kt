package com.packy.createbox.createboax.choosemusic

import com.packy.mvi.base.MviViewModel

class CreateBoxChooseMusicViewModel :
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