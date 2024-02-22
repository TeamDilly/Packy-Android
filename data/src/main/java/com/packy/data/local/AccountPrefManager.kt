package com.packy.data.local

import android.content.Context
import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.LetterSenderReceiverDto
import com.packy.domain.model.auth.SignUp
import com.packy.domain.model.createbox.box.CreateBox
import com.packy.pref.manager.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountPrefManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefManager(
    context,
    ACCOUNT_PREF
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun clearAll(){
        GlobalScope.launch {
            signUp.clear()
            letterSenderReceiver.clear()
            boxDesign.clear()
            createBox.clear()
            shouldShowBoxMotion.clear()
            shouldShowBoxTutorial.clear()
        }
    }

    val signUp = createNonNullMemoryPrefItem<SignUp>(
        "signUp",
        SignUp(
            provider = "kakao",
            nickname = "",
            profileImg = -1,
            pushNotification = false,
            marketingAgreement = false,
            token = "",
            serviceAllow = false,
            personalAllow = false
        ),
        SignUp::class
    )

    val letterSenderReceiver = createMemoryPrefItem<LetterSenderReceiverDto>(
        "LetterSenderReceiver",
        null,
        LetterSenderReceiverDto::class
    )
    val boxDesign = createMemoryPrefItem<BoxDesignDto>(
        "boxDesign",
        null,
        BoxDesignDto::class
    )

    val createBox = createNonNullMemoryPrefItem<CreateBox>(
        "createBoxRequest",
        CreateBox(
            boxId = null,
            envelopeId = null,
            envelopeUrl = null,
            gift = null,
            letterContent = null,
            name = null,
            photo = null,
            receiverName = null,
            senderName = null,
            stickers = emptyList(),
            youtubeUrl = null,
            boxImage = null
        ),
        BoxDesignDto::class
    )

    val shouldShowBoxMotion = createNonNullMemoryPrefItem<Boolean>(
        "shouldShowBoxMotion",
        true,
        Boolean::class
    )

    val shouldShowBoxShareMotion = createNonNullMemoryPrefItem<Boolean>(
        "shouldShowBoxShareMotion",
        true,
        Boolean::class
    )
    val shouldShowBoxTutorial = createNonNullMemoryPrefItem<Boolean>(
        "shouldShowBoxTutorial",
        true,
        Boolean::class
    )

    companion object {
        const val ACCOUNT_PREF = "accountPref"
    }
}