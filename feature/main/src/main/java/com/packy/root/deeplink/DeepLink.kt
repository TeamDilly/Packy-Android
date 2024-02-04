package com.packy.root.deeplink

import android.content.Intent
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.packy.root.deeplink.DeepLinkRoute.GIFT_BOX_OPEN_LINK

object DeepLinkRoute {
    const val URL = "packyforyou://"
    const val LAUNCH_ROUTE = "launchRoute"

    const val GIFT_BOX_OPEN_LINK = "${URL}boxopen/{boxId}"
}