package com.packy.createbox.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.packy.core.animations.asFadeInComposable
import com.packy.core.animations.asFadeInSlidOutComposable
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.createbox.boxaddinfo.BoxAddInfoScreen
import com.packy.createbox.boxchoice.BoxChoiceScreen
import com.packy.createbox.boxguide.BoxGuideScreen
import com.packy.createbox.boxmotion.BoxMotionScreen
import com.packy.createbox.boxshare.BoxShareScreen
import com.packy.createbox.boxtitle.BoxAddTitleScreen

fun NavGraphBuilder.createBoxNavGraph(
    navController: NavHostController,
    closeCreateBox: () -> Unit
) {
    navigation(
        startDestination = CreateBoxRoute.BOX_ADD_INFO,
        route = CreateBoxRoute.CREATE_BOX_NAV_GRAPH,
    ) {
        asRootComposable(
            route = CreateBoxRoute.BOX_ADD_INFO,

            ) {
            BoxAddInfoScreen(
                navController = navController,
                closeCreateBox = closeCreateBox
            )
        }
        asPagingComposable(
            route = CreateBoxRoute.BOX_CHOICE,
        ) {
            BoxChoiceScreen(
                navController = navController,
                closeCreateBox = closeCreateBox
            )
        }
        asPagingComposable(
            route = CreateBoxRoute.BOX_GUIDE_PAGING,
        ) {
            BoxGuideScreen(
                navController = navController,
            )
        }
        asFadeInSlidOutComposable(
            route = CreateBoxRoute.BOX_GUIDE_FADE_IN
        ) {
            BoxGuideScreen(
                navController = navController,
            )
        }
        asFadeInComposable(
            route = CreateBoxRoute.BOX_MOTION + "/{boxId}",
            arguments = listOf(
               navArgument("boxId") {
                   type = androidx.navigation.NavType.IntType
               }
            ),
            enterDuration = 700
        ) {
            val boxId = it.arguments?.getInt("boxId")
            BoxMotionScreen(
                navController = navController,
                boxId = boxId ?: 0,
            )
        }

        asFadeInComposable(
            route = CreateBoxRoute.BOX_ADD_TITLE
        ) {
            BoxAddTitleScreen(
                navController = navController,
            )
        }

        asPagingComposable(
            route = CreateBoxRoute.BOX_SHARE
        ) {
            BoxShareScreen(
                navController = navController,
            )
        }
    }
}

object CreateBoxRoute {
    const val CREATE_BOX_NAV_GRAPH = "createBoxNavGraph"

    const val BOX_CHOICE = "boxChoice"
    const val BOX_GUIDE_PAGING = "boxGuidePaging"
    const val BOX_GUIDE_FADE_IN = "boxGuidePagingFadeIn"
    const val BOX_ADD_INFO = "boxAddInfo"
    const val BOX_MOTION = "boxMotion"
    const val BOX_ADD_TITLE = "boxAddTitle"
    const val BOX_SHARE = "boxShare"
    fun getBoxMotionRoute(
        boxId: Int
    ) = "$BOX_MOTION/$boxId"
}