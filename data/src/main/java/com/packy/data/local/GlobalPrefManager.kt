package com.packy.data.local

import android.content.Context
import com.packy.pref.manager.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalPrefManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : PrefManager(context, DEVICE_PREF) {

    fun clearAll(){

    }

    companion object {
        const val DEVICE_PREF = "globalPref"
    }
}