package com.packy.createbox.createboax.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.packy.core.animations.PaginationAnimation
import com.packy.createbox.createboax.addyourmusic.CreateBoxYourMusicScreen
import com.packy.createbox.createboax.choosemusic.CreateBoxChooseMusicScreen

@Composable
fun CreateBoxNavHost(
    modifier: Modifier = Modifier,
    closeBottomSheet: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CreateBoxBottomSheetRoute.CREATE_BOX_BOTTOM_SHEET_NAV_GRAPH
    ) {
        createBoxBottomSheetNavGraph(navController, closeBottomSheet)
    }
}

fun NavGraphBuilder.createBoxBottomSheetNavGraph(
    navController: NavHostController,
    closeBottomSheet: () -> Unit
) {
    navigation(
        startDestination = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC,
        route = CreateBoxBottomSheetRoute.CREATE_BOX_BOTTOM_SHEET_NAV_GRAPH
    ) {
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC,
        ) {
            CreateBoxChooseMusicScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet
            )
        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_YOUR_MUSIC,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {
            CreateBoxYourMusicScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet
            )
        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PACKY_MUSIC,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PHOTO,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_LATTER,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_GIFT,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {
        }
    }
}

object CreateBoxBottomSheetRoute {
    const val CREATE_BOX_BOTTOM_SHEET_NAV_GRAPH = "createBoxBottomSheetNavGraph"

    const val CREATE_BOX_CHOOSE_MUSIC = "createBoxChooseMusic"
    const val CREATE_BOX_ADD_YOUR_MUSIC = "createBoxAddYourMusic"
    const val CREATE_BOX_ADD_PACKY_MUSIC = "createBoxAddPackyMusic"
    const val CREATE_BOX_ADD_PHOTO = "createBoxAddPhoto"
    const val CREATE_BOX_ADD_LATTER = "createBoxAddLetter"
    const val CREATE_BOX_ADD_GIFT = "createBoxAddGift"
}