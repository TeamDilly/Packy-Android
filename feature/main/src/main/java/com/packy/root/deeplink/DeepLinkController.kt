package com.packy.root.deeplink

import android.content.Intent
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

sealed interface DeepLinkController {
    data object NonDeepLink: DeepLinkController
    data class OpenBox(val boxId: String) : DeepLinkController

    data object CreateBox: DeepLinkController

    fun getDeepLinkController(backStackEntry: NavBackStackEntry): DeepLinkController {
        // 딥링크 인텐트를 추출
        val intent = backStackEntry.arguments?.getParcelable("android-support-nav:controller:deepLinkIntent", Intent::class.java)

        // 인텐트의 데이터로부터 URI를 가져오기
        val deepLinkUri = intent?.data

        // URI를 통해 어떤 딥링크인지 판별
        return when (deepLinkUri?.toString()) {
            DeepLinkRoute.MAIN -> DeepLinkController.NonDeepLink
            DeepLinkRoute.CREATE_BOX -> DeepLinkController.CreateBox
            else -> DeepLinkController.NonDeepLink
        }
    }
}