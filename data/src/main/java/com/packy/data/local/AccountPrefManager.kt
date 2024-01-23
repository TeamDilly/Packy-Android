package com.packy.data.local

import android.content.Context
import com.packy.domain.model.auth.SignUp
import com.packy.pref.manager.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountPrefManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefManager(context, ACCOUNT_PREF) {

    val signUp = createNonNullMemoryPrefItem<SignUp>(
        "globalSample", SignUp(
            provider = "kakao",
            nickname = "",
            profileImg = -1,
            pushNotification = false,
            marketingAgreement = false,
            token = "",
            serviceAllow = false,
            personalAllow = false
        ), SignUp::class
    )



    companion object {
        const val ACCOUNT_PREF = "accountPref"
    }
}