package com.packy.createbox.boxguide

import com.packy.mvi.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoxGuideViewModel @Inject constructor() :
    MviViewModel<BoxGuideIntent, BoxGuideState, BoxGuideEffect>() {
    override fun createInitialState(): BoxGuideState = BoxGuideState(

    )

    override fun handleIntent() {

    }
}