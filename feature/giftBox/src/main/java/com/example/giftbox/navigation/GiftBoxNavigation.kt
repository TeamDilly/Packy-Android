package com.example.giftbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.example.giftbox.boxerror.GiftBoxErrorScreen
import com.example.giftbox.boxmotion.GiftBoxMotionScreen
import com.example.giftbox.boxroot.GiftBoxRootScreen
import com.packy.core.animations.asRootComposable
import com.packy.domain.model.getbox.GiftBox
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.gitBoxNavGraph(
    navController: NavHostController,
    boxId: String
) {
    navigation(
        startDestination = GiftBoxRoute.GIFT_BOX_ROOT,
        route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH
    ) {
        asRootComposable(
            route = GiftBoxRoute.GIFT_BOX_ROOT
        ) {
            GiftBoxRootScreen(
                navController = navController,
                boxId = boxId
            )
        }
        asRootComposable(
            route = GiftBoxRoute.GIFT_BOX_MOTION + "/{giftBox}",
        ) {
            val giftBoxJson = it.arguments?.getString("giftBox")
            val giftBox = giftBoxJson?.let { json -> Json.decodeFromString<GiftBox>(json) }
            if (giftBox == null) {
                GiftBoxErrorScreen(
                    message = "Message"
                )
            } else {
                GiftBoxMotionScreen(giftBox = giftBox)
            }
        }
    }
}

object GiftBoxRoute {
    const val GIFT_BOX_NAV_GRAPH = "giftBoxNavGraph"
    const val GIFT_BOX_ROOT = "giftBoxRoot"
    const val GIFT_BOX_ERROR = "giftBoxError"
    const val GIFT_BOX_ARR = "giftBoxArr"
    const val GIFT_BOX_MOTION = "giftBoxMotion"
    const val GIFT_BOX_DETAIL_OPEN = "giftBoxDetailOpen"

    fun getGiftBoxMotionRoute(giftBox: GiftBox): String {
        val giftBoxJson = Json.encodeToString(giftBox.toUrlEncoding())
        return "${GIFT_BOX_MOTION}/$giftBoxJson"
    }
}