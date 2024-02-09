package com.example.giftbox.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.giftbox.boxdetailopen.GiftBoxDetailOpenScreen
import com.example.giftbox.boxerror.GiftBoxErrorScreen
import com.example.giftbox.boxmotion.GiftBoxMotionScreen
import com.example.giftbox.boxroot.GiftBoxRootScreen
import com.example.giftbox.giftarr.GiftBoxArrScreen
import com.example.giftbox.navigation.GiftBoxRoute.GIFT_BOX_ARG
import com.example.giftbox.navigation.GiftBoxRoute.GIFT_BOX_ID_ARG
import com.example.giftbox.navigation.GiftBoxRoute.GIFT_BOX_SKIP_ARR_ARG
import com.packy.core.animations.asFadeInComposable
import com.packy.core.animations.asFadeInSlidOutComposable
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.domain.model.getbox.GiftBox
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.giftBoxNavGraph(
    navController: NavHostController,
    closeGiftBox: () -> Unit,
) {
    navigation(
        startDestination = GiftBoxRoute.GIFT_BOX_ROOT,
        route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH
    ) {

        asFadeInComposable(
            route = GiftBoxRoute.GIFT_BOX_ROOT + "/{$GIFT_BOX_ID_ARG}" + "?" + "skipArr={$GIFT_BOX_SKIP_ARR_ARG}",
            arguments = listOf(
                navArgument(GIFT_BOX_SKIP_ARR_ARG) {
                    type = NavType.BoolType
                    defaultValue = true
                }
            )
        ) {
            val skipArr = it.arguments?.getBoolean(GIFT_BOX_SKIP_ARR_ARG) ?: true
            GiftBoxRootScreen(
                navController = navController,
                skipArr = skipArr
            )
        }
        asPagingComposable(
            route = GiftBoxRoute.GIFT_BOX_MOTION + "/{$GIFT_BOX_ARG}",
        ) {
            val giftBoxJson = it.arguments?.getString(GIFT_BOX_ARG)
            val giftBox = giftBoxJson?.let { json -> Json.decodeFromString<GiftBox>(json) }
            if (giftBox == null) {
                GiftBoxErrorScreen(
                    message = "Message",
                    closeGiftBox = closeGiftBox
                )
            } else {
                GiftBoxMotionScreen(
                    navController = navController,
                    giftBox = giftBox
                )
            }
        }
        asPagingComposable(
            route = GiftBoxRoute.GIFT_BOX_ARR + "/{$GIFT_BOX_ARG}",
        ) {
            GiftBoxArrScreen(
                navController = navController,
            )
        }
        asPagingComposable(
            route = GiftBoxRoute.GIFT_BOX_DETAIL_OPEN + "/{$GIFT_BOX_ARG}",
        ) {
            GiftBoxDetailOpenScreen(
                navController = navController,
                closeGiftBox= closeGiftBox
            )
        }
        asFadeInSlidOutComposable(
            route = GiftBoxRoute.GIFT_BOX_DETAIL_OPEN_FADE + "/{$GIFT_BOX_ARG}",
            enterDuration = 1000
        ) {
            GiftBoxDetailOpenScreen(
                navController = navController,
                closeGiftBox = closeGiftBox
            )
        }
        asPagingComposable(
            route = GiftBoxRoute.GIFT_BOX_ERROR,
        ) {
            GiftBoxErrorScreen(
                message = "Message",
                closeGiftBox = closeGiftBox
            )
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
    const val GIFT_BOX_DETAIL_OPEN_FADE = "giftBoxDetailOpenFade"

    const val GIFT_BOX_ID_ARG = "giftBoxIdArg"
    const val GIFT_BOX_SKIP_ARR_ARG = "skipArr"
    const val GIFT_BOX_ARG = "giftBoxArg"

    fun getGiftBoxRootRoute(giftBoxId: Long): String {
        return "$GIFT_BOX_ROOT/$giftBoxId"
    }

    fun getGiftBoxMotionRoute(giftBox: GiftBox): String {
        val giftBoxJson = Json.encodeToString(giftBox.toUrlEncoding())
        return "${GIFT_BOX_MOTION}/$giftBoxJson"
    }

    fun getGiftBoxArrRoute(giftBox: GiftBox): String {
        val giftBoxJson = Json.encodeToString(giftBox.toUrlEncoding())
        return "${GIFT_BOX_ARR}/$giftBoxJson"
    }

    fun getGiftBoxDetailOpenRoute(giftBox: GiftBox): String {
        val giftBoxJson = Json.encodeToString(giftBox.toUrlEncoding())
        return "${GIFT_BOX_DETAIL_OPEN}/$giftBoxJson"
    }

    fun getGiftBoxDetailOpenFadeRoute(giftBox: GiftBox): String {
        val giftBoxJson = Json.encodeToString(giftBox.toUrlEncoding())
        return "${GIFT_BOX_DETAIL_OPEN_FADE}/$giftBoxJson"
    }

    fun getGiftBoxArg(savedStateHandle: SavedStateHandle): GiftBox? {
        val giftBoxJson = savedStateHandle.get<String>(GIFT_BOX_ARG)
        return giftBoxJson?.let { Json.decodeFromString<GiftBox>(it) }
    }
}