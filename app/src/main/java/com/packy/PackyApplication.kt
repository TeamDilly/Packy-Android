package com.packy

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.packy.common.notification.NotificationConstants
import com.packy.common.notification.NotificationConstants.PACKY_CHANNEL
import dagger.hilt.android.HiltAndroidApp
import io.branch.referral.Branch
import javax.inject.Inject

@HiltAndroidApp
class PackyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_app_key))
        initFirebase()
        initBranch()
        initNotificationChannel()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initBranch() {
        if (BuildConfig.DEBUG) Branch.enableTestMode()
        Branch.getAutoInstance(this)
    }

    private fun initNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).apply {
                createNotificationChannel(PACKY_CHANNEL)
            }
        }
    }
}