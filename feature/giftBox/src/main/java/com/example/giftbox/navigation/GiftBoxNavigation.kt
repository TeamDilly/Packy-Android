package com.example.giftbox.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
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
import com.example.giftbox.navigation.GiftBoxArgs.GIFT_BOX_ID_ARG
import com.example.giftbox.navigation.GiftBoxArgs.GIFT_BOX_JSON_ARG
import com.example.giftbox.navigation.GiftBoxArgs.GIFT_BOX_SHOULD_SHOW_SHARED_ARG
import com.example.giftbox.navigation.GiftBoxArgs.GIFT_BOX_SKIP_ARR_ARG
import com.packy.core.animations.asFadeInComposable
import com.packy.core.animations.asFadeInSlidOutComposable
import com.packy.core.animations.asPagingComposable
import com.packy.core.common.PackyJson
import com.packy.core.navigiation.NavScreens
import com.packy.core.navigiation.replaceArguments
import com.packy.domain.model.getbox.GiftBox
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.giftBoxNavGraph(
    navController: NavHostController,
    closeGiftBox: () -> Unit,
    moveToShared: (Long) -> Unit,
) {
    navigation(
        startDestination = GiftBoxScreens.GiftBoxRoot.name,
        route = GiftBoxScreens.GiftBoxNavGraph.name
    ) {

        asFadeInComposable(
            route = GiftBoxScreens.GiftBoxRoot.name,
            arguments = listOf(
                navArgument(GIFT_BOX_ID_ARG) {
                    type = NavType.LongType
                },
                navArgument(GIFT_BOX_SKIP_ARR_ARG) {
                    type = NavType.BoolType
                },
                navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                    type = NavType.BoolType
                },
            )
        ) {
            val skipArr = it.arguments?.getBoolean(GIFT_BOX_SKIP_ARR_ARG) ?: true
            val shouldShowShared = it.arguments?.getBoolean(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) ?: false
            GiftBoxRootScreen(
                navController = navController,
                skipArr = skipArr,
                shouldShowShared = shouldShowShared
            )
        }
        asPagingComposable(
            route = GiftBoxScreens.GiftBoxMotion.name,
        ) {
            val giftBoxJson = it.arguments?.getString(GIFT_BOX_JSON_ARG)
            val giftBox = giftBoxJson?.let { json -> Json.decodeFromString<GiftBox>(json) }
            if (giftBox == null) {
                GiftBoxErrorScreen(
                    message = "Message",
                    closeGiftBox = closeGiftBox,
                    giftBoxId = null
                )
            } else {
                GiftBoxMotionScreen(
                    navController = navController,
                    giftBox = giftBox
                )
            }
        }
        asPagingComposable(
            route = GiftBoxScreens.GiftBoxArr.name,
        ) {
            GiftBoxArrScreen(
                navController = navController,
                closeGiftBox = closeGiftBox,
            )
        }
        asPagingComposable(
            route = GiftBoxScreens.GiftBoxDetailOpen.name,
            arguments = listOf(
                navArgument(GIFT_BOX_JSON_ARG) {
                    type = NavType.StringType
                },
                navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                    type = NavType.BoolType
                },
            )
        ) {
            GiftBoxDetailOpenScreen(
                navController = navController,
                closeGiftBox = closeGiftBox,
                moveToShared = moveToShared,
                showBackArrow = false
            )
        }
        asFadeInSlidOutComposable(
            route = GiftBoxScreens.GiftBoxDetailOpenFade.name,
            enterDuration = 1000,
            arguments = listOf(
                navArgument(GIFT_BOX_JSON_ARG) {
                    type = NavType.StringType
                },
                navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                    type = NavType.BoolType
                },
            )
        ) {
            GiftBoxDetailOpenScreen(
                navController = navController,
                closeGiftBox = closeGiftBox,
                moveToShared = moveToShared,
                showBackArrow = true
            )
        }
        asPagingComposable(
            route = GiftBoxScreens.GiftBoxError.name,
            arguments = listOf(
                navArgument(GIFT_BOX_ID_ARG) {
                    type = NavType.LongType
                }
            )
        ) {
            val giftBoxId = it.arguments?.getString(GIFT_BOX_ID_ARG)?.toLongOrNull()
            GiftBoxErrorScreen(
                message = "Message",
                closeGiftBox = closeGiftBox,
                giftBoxId = giftBoxId
            )
        }
    }
}

sealed class GiftBoxScreens(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) : NavScreens(_route = route, _navArguments = navArguments) {
    data object GiftBoxNavGraph : GiftBoxScreens("giftBoxNavGraph")

    data object GiftBoxRoot : GiftBoxScreens(
        route = "giftBoxRoot",
        navArguments = listOf(
            navArgument(GIFT_BOX_ID_ARG) {
                type = NavType.LongType
            },
            navArgument(GIFT_BOX_SKIP_ARR_ARG) {
                type = NavType.BoolType
            },
            navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                type = NavType.BoolType
            }
        )
    ) {
        fun create(
            giftBoxId: Long,
            skipArr: Boolean = true,
            shouldShowShared: Boolean
        ) = name
            .replaceArguments(navArguments[0], giftBoxId.toString())
            .replaceArguments(navArguments[1], skipArr.toString())
            .replaceArguments(navArguments[2], shouldShowShared.toString())
    }

    data object GiftBoxError : GiftBoxScreens(
        route = "giftBoxError",
        navArguments = listOf(
            navArgument(GIFT_BOX_ID_ARG) {
                type = NavType.LongType
            }
        )
    ) {
        fun create(giftBoxId: Long) = name.replaceArguments(navArguments.first(), giftBoxId.toString())
    }

    data object GiftBoxArr : GiftBoxScreens(
        route = "giftBoxArr",
        navArguments = listOf(
            navArgument(GIFT_BOX_JSON_ARG) {
                type = NavType.StringType
            }
        )
    ) {
        fun create(giftBox: GiftBox): String {
            val giftBoxJson = PackyJson.encodeToString(giftBox.toUrlEncoding())
            return name.replaceArguments(
                navArguments.first(),
                giftBoxJson
            )
        }
    }

    data object GiftBoxMotion : GiftBoxScreens(
        route = "giftBoxMotion",
        navArguments = listOf(
            navArgument(GIFT_BOX_JSON_ARG) {
                type = NavType.StringType
            }
        )
    ) {
        fun create(giftBox: GiftBox): String {
            val giftBoxJson = PackyJson.encodeToString(giftBox.toUrlEncoding())
            return name.replaceArguments(
                navArguments.first(),
                giftBoxJson
            )
        }
    }

    data object GiftBoxDetailOpen : GiftBoxScreens(
        route = "giftBoxDetailOpen",
        navArguments = listOf(
            navArgument(GIFT_BOX_JSON_ARG) {
                type = NavType.StringType
            },
            navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                type = NavType.BoolType
            }
        )
    ) {
        fun create(
            giftBox: GiftBox,
            shouldShowShared: Boolean
        ): String {
            val giftBoxJson = PackyJson.encodeToString(giftBox.toUrlEncoding())
            return name.replaceArguments(
                navArguments[0],
                giftBoxJson
            ).replaceArguments(
                navArguments[1],
                shouldShowShared.toString()
            )
        }
    }

    data object GiftBoxDetailOpenFade : GiftBoxScreens(
        route = "giftBoxDetailOpenFade",
        navArguments = listOf(
            navArgument(GIFT_BOX_JSON_ARG) {
                type = NavType.StringType
            },
            navArgument(GIFT_BOX_SHOULD_SHOW_SHARED_ARG) {
                type = NavType.BoolType
            }
        )
    ) {
        fun create(giftBox: GiftBox, shouldShowShared: Boolean): String {
            val giftBoxJson = PackyJson.encodeToString(giftBox.toUrlEncoding())
            return name.replaceArguments(
                navArguments[0],
                giftBoxJson
            ).replaceArguments(
                navArguments[1],
                shouldShowShared.toString()
            )
        }
    }
}

object GiftBoxArgs {
    const val GIFT_BOX_ID_ARG = "giftBoxId"
    const val GIFT_BOX_SKIP_ARR_ARG = "skipArr"
    const val GIFT_BOX_SHOULD_SHOW_SHARED_ARG = "shouldShowShared"
    const val GIFT_BOX_JSON_ARG = "giftBoxJson"

    fun getGiftBoxArg(savedStateHandle: SavedStateHandle): GiftBox? {
        val giftBoxJson = savedStateHandle.get<String>(GIFT_BOX_JSON_ARG)
        return giftBoxJson?.let { PackyJson.decodeFromString<GiftBox>(it) }
    }
}
