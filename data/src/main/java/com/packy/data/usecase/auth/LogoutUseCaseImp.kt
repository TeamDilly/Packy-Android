package com.packy.data.usecase.auth

import android.content.Context
import android.content.Intent
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.packy.data.local.AccountPrefManager
import com.packy.data.local.GlobalPrefManager
import com.packy.domain.usecase.auth.LogoutUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject    

class LogoutUseCaseImp @Inject constructor(
    private val accountPrefManager: AccountPrefManager,
    private val globalPrefManager: GlobalPrefManager,
    @ApplicationContext private val appContext: Context
) : LogoutUseCase {
    override suspend fun logout() {
        accountPrefManager.clearAll()
        globalPrefManager.clearAll()
        clearAnalytics()

        val pm = appContext.packageManager
        val intent = pm.getLaunchIntentForPackage(appContext.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent!!.component)
        appContext.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    private fun clearAnalytics(){
        Firebase.analytics.resetAnalyticsData()
    }
}