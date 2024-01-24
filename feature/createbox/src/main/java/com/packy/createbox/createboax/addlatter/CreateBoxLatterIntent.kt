package com.packy.createbox.createboax.addlatter

import com.packy.domain.model.createbox.LatterEnvelope
import com.packy.mvi.mvi.MviIntent
import com.packy.mvi.mvi.SideEffect
import com.packy.mvi.mvi.UiState

sealed interface CreateBoxLatterIntent : MviIntent {
    data object OnCloseClick : CreateBoxLatterIntent
    data object OnSaveClick : CreateBoxLatterIntent
    data class ChangeLatterText(val latter: String) : CreateBoxLatterIntent
    data class ChangeEnvelope(val envelopeId: Int) : CreateBoxLatterIntent
    data class GetEnvelope(val envelopeList: List<LatterEnvelope>) : CreateBoxLatterIntent
}

data class CreateBoxLatterState(
    val latterText: String,
    val envelopeId: Int,
    val envelopeList: List<LatterEnvelope>
) : UiState{
    fun getLatterEnvelope(): LatterEnvelope? = envelopeList.firstOrNull{
        it.id == envelopeId
    }
}

sealed interface CreateBoxLatterEffect : SideEffect {
    data object CloseBottomSheet : CreateBoxLatterEffect
    data class SaveLatter(
        val envelopId: Int,
        val envelopUri: String,
        val latterText: String,
    ): CreateBoxLatterEffect
    data object OverFlowLatterText : CreateBoxLatterEffect
}