package com.packy.data.local

import android.content.Context
import com.packy.pref.manager.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountPrefManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefManager(context, ACCOUNT_PREF) {


    companion object {
        const val ACCOUNT_PREF = "accountPref"
    }
}