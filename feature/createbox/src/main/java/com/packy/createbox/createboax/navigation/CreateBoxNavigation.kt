package com.packy.createbox.createboax.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.packy.createbox.createboax.choosemusic.CreateBoxChooseMusicScreen

@Composable
fun CreateBoxNavHost(
    closeBottomSheet: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
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
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PACKY_MUSIC,
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PHOTO,
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_LATTER,
        ) {

        }
        composable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_GIFT,
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