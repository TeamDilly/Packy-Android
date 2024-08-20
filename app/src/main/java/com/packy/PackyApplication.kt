package com.packy

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
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
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initBranch() {
        if (BuildConfig.DEBUG) Branch.enableTestMode()
        Branch.getAutoInstance(this)
    }
}