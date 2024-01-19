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
import com.packy.createbox.createboax.addpackymusic.CreateBoxPackyMusicScreen
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoScreen
import com.packy.createbox.createboax.addyourmusic.CreateBoxYourMusicScreen
import com.packy.createbox.createboax.choosemusic.CreateBoxChooseMusicScreen

@Composable
fun CreateBoxNavHost(
    modifier: Modifier = Modifier,
    startDestination: String,
    closeBottomSheet: () -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        createBoxBottomSheetNavGraph(navController, closeBottomSheet, startDestination)
    }
}

fun NavGraphBuilder.createBoxBottomSheetNavGraph(
    navController: NavHostController,
    closeBottomSheet: () -> Unit,
    startDestination: String = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC
) {
    navigation(
        startDestination = startDestination,
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
            CreateBoxPackyMusicScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet
            )
        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PHOTO,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {
            CreateBoxAddPhotoScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet
            )
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