package com.packy.createbox.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.packy.core.animations.asFadeInComposable
import com.packy.core.animations.asFadeInSlidOutComposable
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.core.navigiation.NavScreens
import com.packy.core.navigiation.replaceArguments
import com.packy.createbox.boxaddinfo.BoxAddInfoScreen
import com.packy.createbox.boxchoice.BoxChoiceScreen
import com.packy.createbox.boxguide.BoxGuideScreen
import com.packy.createbox.boxmotion.BoxMotionScreen
import com.packy.createbox.boxsharemotion.BoxShareMotionScreen
import com.packy.createbox.boxshare.BoxShareScreen
import com.packy.createbox.boxtitle.BoxAddTitleScreen
import com.packy.createbox.navigation.CreateBoxArgs.CREATED_BOX_ID
import com.packy.createbox.navigation.CreateBoxArgs.LOTTIE_ANIMATION_URL
import com.packy.lib.ext.toEncoding

fun NavGraphBuilder.createBoxNavGraph(
    navController: NavHostController,
    closeCreateBox: () -> Unit,
    moveToHomeClear: () -> Unit
) {
    navigation(
        startDestination = CreateBoxScreens.BoxAddInfo.name,
        route = CreateBoxScreens.CreateBoxNavGraph.name,
    ) {
        asRootComposable(
            route = CreateBoxScreens.BoxAddInfo.name,

            ) {
            BoxAddInfoScreen(
                navController = navController,
                closeCreateBox = closeCreateBox
            )
        }
        asPagingComposable(
            route = CreateBoxScreens.BoxChoice.name,
        ) {
            BoxChoiceScreen(
                navController = navController,
                closeCreateBox = closeCreateBox
            )
        }
        asPagingComposable(
            route = CreateBoxScreens.BoxGuidePaging.name,
        ) {
            BoxGuideScreen(
                navController = navController,
            )
        }
        asFadeInSlidOutComposable(
            route = CreateBoxScreens.BoxGuideFadeIn.name,
            enterDuration = 700
        ) {
            BoxGuideScreen(
                navController = navController,
            )
        }
        asFadeInComposable(
            route = CreateBoxScreens.BoxMotion.name,
            arguments = listOf(
                navArgument(LOTTIE_ANIMATION_URL) {
                    type = NavType.StringType
                }
            ),
            enterDuration = 700
        ) {
            val lottieAnimation = it.arguments?.getString(LOTTIE_ANIMATION_URL)
            BoxMotionScreen(
                navController = navController,
                lottieAnimation = lottieAnimation ?: ""
            )
        }
        asFadeInComposable(
            route = CreateBoxScreens.BoxShareMotion.name,
            arguments = listOf(
                navArgument(LOTTIE_ANIMATION_URL) {
                    type = NavType.StringType
                },
                navArgument(CREATED_BOX_ID) {
                    type = NavType.LongType
                },
            ),
        ) {
            val lottieAnimation = it.arguments?.getString(LOTTIE_ANIMATION_URL)
            val createdBoxId = it.arguments?.getLong(CREATED_BOX_ID)
                ?: throw IllegalArgumentException("createdBoxId is null")
            BoxShareMotionScreen(
                navController = navController,
                lottieAnimation = lottieAnimation ?: "",
                createdBoxId = createdBoxId,
            )
        }

        asFadeInComposable(
            route = CreateBoxScreens.BoxAddTitle.name,
        ) {
            BoxAddTitleScreen(
                navController = navController,
            )
        }

        asPagingComposable(
            route = CreateBoxScreens.BoxShare.name,
            arguments = listOf(
                navArgument(CREATED_BOX_ID) {
                    type = NavType.LongType
                },
            ),
        ) {
            BoxShareScreen(
                navController = navController,
                moveToHomeClear = moveToHomeClear,
            )
        }


        asFadeInComposable(
            route = CreateBoxScreens.BoxShareFadeIn.name,
            enterDuration = 700,
            arguments = listOf(
                navArgument(CREATED_BOX_ID) {
                    type = NavType.LongType
                },
            ),
        ) {
            BoxShareScreen(
                navController = navController,
                moveToHomeClear = moveToHomeClear,
            )
        }
    }
}

sealed class CreateBoxScreens(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) : NavScreens(_route = route, _navArguments = navArguments) {
    data object CreateBoxNavGraph : CreateBoxScreens("createBoxNavGraph")
    data object BoxChoice : CreateBoxScreens("boxChoice")
    data object BoxGuidePaging : CreateBoxScreens("boxGuidePaging")
    data object BoxGuideFadeIn : CreateBoxScreens("boxGuideFadeIn")
    data object BoxAddInfo : CreateBoxScreens("boxAddInfo")
    data object BoxMotion : CreateBoxScreens(
        route = "boxMotion",
        navArguments = listOf(
            navArgument(LOTTIE_ANIMATION_URL) {
                type = NavType.StringType
            }
        )
    ) {
        fun create(lottieAnimation: String) = name.replaceArguments(navArguments.first(), lottieAnimation.toEncoding())
    }

    data object BoxShareMotion : CreateBoxScreens(
        route = "boxShareMotion",
        navArguments = listOf(
            navArgument(LOTTIE_ANIMATION_URL) {
                type = NavType.StringType
            },
            navArgument(CREATED_BOX_ID) {
                type = NavType.LongType
            }
        )
    ) {
        fun create(lottieAnimation: String, createdBoxId: Long) =
            name.replaceArguments(navArguments.first(), lottieAnimation.toEncoding())
                .replaceArguments(navArguments[1], createdBoxId.toString())
    }

    data object BoxAddTitle : CreateBoxScreens("boxAddTitle")
    data object BoxShare : CreateBoxScreens(
        route = "boxShare",
        navArguments = listOf(
            navArgument(CREATED_BOX_ID) {
                type = NavType.LongType
            }
        )
    ) {
        fun create(createdBoxId: Long) =
            name.replaceArguments(navArguments.first(), createdBoxId.toString())
    }

    data object BoxShareFadeIn : CreateBoxScreens(
        route = "boxShareFadeIn",
        navArguments = listOf(
            navArgument(CREATED_BOX_ID) {
                type = NavType.LongType
            }
        )
    ) {
        fun create(createdBoxId: Long) =
            name.replaceArguments(navArguments.first(), createdBoxId.toString())
    }
}

object CreateBoxArgs {
    const val LOTTIE_ANIMATION_URL = "lottieAnimation"
    const val CREATED_BOX_ID = "createdBoxId"
}