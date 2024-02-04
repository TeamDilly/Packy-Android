package com.packy.root.deeplink

import android.content.Intent
import androidx.navigation.NavDeepLink
import androidx.navigation.navDeepLink
import com.packy.root.deeplink.DeepLinkRoute.GIFT_BOX_OPEN_LINK

val deepLinkMap = buildList<NavDeepLink> {
    add(
        navDeepLink {
            uriPattern = GIFT_BOX_OPEN_LINK
            action = Intent.ACTION_VIEW
        }
    )
}

object DeepLinkRoute {
    const val URL = "https://packyforyou.shop"
    const val LAUNCH_ROUTE = "launchRoute"

    const val GIFT_BOX_OPEN_LINK = "$URL/boxopen/{id}"
}