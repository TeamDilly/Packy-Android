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
import com.packy.createbox.boxsharemotion.BoxShareMotionScreen
import com.packy.createbox.boxshare.BoxShareScreen
import com.packy.createbox.boxtitle.BoxAddTitleScreen
import com.packy.createbox.navigation.CreateBoxArgs.CREATED_BOX_ID
import com.packy.createbox.navigation.CreateBoxArgs.MOTION_BOX_ID

fun NavGraphBuilder.createBoxNavGraph(
    navController: NavHostController,
    closeCreateBox: () -> Unit,
    moveToHomeClear: () -> Unit
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
            route = CreateBoxRoute.BOX_MOTION + "/{$MOTION_BOX_ID}",
            arguments = listOf(
               navArgument(MOTION_BOX_ID) {
                   type = androidx.navigation.NavType.IntType
               }
            ),
            enterDuration = 700
        ) {
            val boxId = it.arguments?.getInt(MOTION_BOX_ID)
            BoxMotionScreen(
                navController = navController,
                boxId = boxId ?: 0,
            )
        }
        asFadeInComposable(
            route = CreateBoxRoute.BOX_SHARE_MOTION + "/{$MOTION_BOX_ID}" + "/{$CREATED_BOX_ID}",
            arguments = listOf(
                navArgument(MOTION_BOX_ID) {
                    type = androidx.navigation.NavType.IntType
                },
                navArgument(CREATED_BOX_ID) {
                    type = androidx.navigation.NavType.StringType
                }
            ),
        ) {
            val boxId = it.arguments?.getInt(MOTION_BOX_ID)
            BoxShareMotionScreen(
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
            route = CreateBoxRoute.BOX_SHARE + "/{$CREATED_BOX_ID}",
            arguments = listOf(
                navArgument(CREATED_BOX_ID) {
                    type = androidx.navigation.NavType.StringType
                }
            ),
        ) {
            BoxShareScreen(
                navController = navController,
                moveToHomeClear = moveToHomeClear,
            )
        }


        asFadeInComposable(
            route = CreateBoxRoute.BOX_SHARE_FADE_IN,
            enterDuration = 700
        ) {
            BoxShareScreen(
                navController = navController,
                moveToHomeClear = moveToHomeClear,
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
    const val BOX_SHARE_MOTION = "boxShareMotion"
    const val BOX_ADD_TITLE = "boxAddTitle"
    const val BOX_SHARE = "boxShare"
    const val BOX_SHARE_FADE_IN = "boxShareFadeIn"
    fun getBoxMotionRoute(
        boxId: Int
    ) = "$BOX_MOTION/$boxId"

    fun getBoxShareMotionRoute(
        boxId: Int,
        createdBoxId: String
    ) = "$BOX_SHARE_MOTION/$boxId/$createdBoxId"

    fun getBoxShareRoute(
        createdBoxId: String
    ) = "$BOX_SHARE/$createdBoxId"
}

object CreateBoxArgs{
    const val MOTION_BOX_ID = "motionBoxId"
    const val CREATED_BOX_ID = "createdBoxId"
}