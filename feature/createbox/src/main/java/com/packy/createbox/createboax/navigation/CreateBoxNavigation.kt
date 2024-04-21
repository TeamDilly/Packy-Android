package com.packy.createbox.createboax.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.packy.core.animations.asPagingComposable
import com.packy.createbox.createboax.addpackymusic.CreateBoxPackyMusicScreen
import com.packy.createbox.createboax.addpackymusic.PackyMusic
import com.packy.createbox.createboax.addyourmusic.CreateBoxYourMusicScreen
import com.packy.createbox.createboax.choosemusic.CreateBoxChooseMusicScreen

@Composable
fun CreateBoxNavHost(
    modifier: Modifier = Modifier,
    closeBottomSheet: (Boolean) -> Unit,
    saveMusic: (String) -> Unit,
    suggestionMusic: List<PackyMusic>,
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CreateBoxBottomSheetRoute.CREATE_BOX_BOTTOM_SHEET_NAV_GRAPH
    ) {
        createBoxBottomSheetNavGraph(
            navController,
            closeBottomSheet,
            saveMusic,
            suggestionMusic,
        )
    }
}

fun NavGraphBuilder.createBoxBottomSheetNavGraph(
    navController: NavHostController,
    closeBottomSheet: (Boolean) -> Unit,
    saveMusic: (String) -> Unit,
    suggestionMusic: List<PackyMusic>
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
                closeBottomSheet = closeBottomSheet,
            )
        }
        asPagingComposable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_YOUR_MUSIC,
        ) {
            CreateBoxYourMusicScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet,
                saveMusic = saveMusic
            )
        }
        asPagingComposable(
            route = CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PACKY_MUSIC,
        ) {
            CreateBoxPackyMusicScreen(
                navController = navController,
                closeBottomSheet = closeBottomSheet,
                saveMusic = saveMusic,
                suggestionMusic = suggestionMusic
            )
        }
    }
}

object CreateBoxBottomSheetRoute {
    const val CREATE_BOX_BOTTOM_SHEET_NAV_GRAPH = "createBoxBottomSheetNavGraph"

    const val CREATE_BOX_CHOOSE_MUSIC = "createBoxChooseMusic"
    const val CREATE_BOX_ADD_YOUR_MUSIC = "createBoxAddYourMusic"
    const val CREATE_BOX_ADD_PACKY_MUSIC = "createBoxAddPackyMusic"
}