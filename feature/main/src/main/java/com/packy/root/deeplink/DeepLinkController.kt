package com.packy.root.deeplink

import android.content.Intent
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink

sealed interface DeepLinkController {
    data object NonDeepLink: DeepLinkController
    data class OpenBox(val boxId: String) : DeepLinkController
}