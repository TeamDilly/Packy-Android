package com.packy.data.local

import android.content.Context
import com.packy.data.model.createbox.BoxDesignDto
import com.packy.data.model.createbox.LetterSenderReceiverDto
import com.packy.domain.model.auth.SignUp
import com.packy.pref.manager.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountPrefManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefManager(
    context,
    ACCOUNT_PREF
) {

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
    val boxDesign = createMemoryPrefItem(
        "boxDesign",
        null,
        BoxDesignDto::class
    )
    val shouldShowBoxMotion = createNonNullMemoryPrefItem(
        "shouldShowBoxMotion",
        true,
        Boolean::class
    )
    val shouldShowBoxTutorial = createNonNullMemoryPrefItem(
        "shouldShowBoxTutorial",
        true,
        Boolean::class
    )

    companion object {
        const val ACCOUNT_PREF = "accountPref"
    }
}