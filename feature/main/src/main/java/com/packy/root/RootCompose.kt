package com.packy.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.giftbox.navigation.GiftBoxScreens
import com.example.home.root.HomeRoute.HOME_ROOT
import com.example.home.navigation.SettingsRoute
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.dialog.PackyDialogInfo
import com.packy.core.values.Strings
import com.packy.core.values.Strings.CREATE_BOX_CANCEL_BOX
import com.packy.createbox.navigation.CreateBoxScreens

import com.packy.feature.main.R
import com.packy.root.navigation.CommonScreen
import com.packy.root.navigation.MainScreens
import com.packy.root.navigation.PackyNavHost
import com.packy.root.webview.ComposeWebViewScreen
import kotlinx.coroutines.launch

@Composable
fun RootCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RootComposeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val kakaoLinkScheme = stringResource(id = R.string.kakao_link_scheme)

    var globalPopUp by rememberSaveable { mutableStateOf<PackyDialogInfo?>(null) }

    if (globalPopUp != null) {
        PackyDialog(packyDialogInfo = globalPopUp!!)
    }

    PackyNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainScreens.LaunchNavGraph.name,
        logout = {
            scope.launch {
                viewModel.logout()
                navController.navigate(MainScreens.LaunchNavGraph.name) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        },
        moveToHomeClear = {
            navController.navigate(HOME_ROOT) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        },
        moveToBoxDetail = { boxId, shouldShowShared ->
            navController.navigate(
                GiftBoxScreens.GiftBoxRoot.create(
                    giftBoxId = boxId,
                    shouldShowShared = shouldShowShared
                )
            )
        },
        moveToCreateBox = {
            navController.navigate(CreateBoxScreens.CreateBoxNavGraph.name)
        },
        kakaoLinkScheme = kakaoLinkScheme,
        moveSettings = {
            navController.navigate(SettingsRoute.SETTINGS_NAV_GRAPH)
        },
        moveToShared = { giftBoxId ->
            navController.navigate(
                CreateBoxScreens.BoxShare.create(
                    createdBoxId = giftBoxId
                )
            )
        },
        closeCreateBox = {
            val closePackyDialog = PackyDialogInfo(
                title = CREATE_BOX_CANCEL_BOX,
                dismiss = Strings.CONFIRM,
                confirm = Strings.CANCEL,
                onDismiss = {
                    navController.navigate(HOME_ROOT) {
                        popUpTo(navController.graph.id) { inclusive = true }
                        launchSingleTop = true
                    }
                    globalPopUp = null
                },
                onConfirm = {
                    globalPopUp = null
                },
                backHandler = {
                    globalPopUp = null
                }
            )
            globalPopUp = closePackyDialog
        },
        moveToBoxOpenMotion = {
            navController.navigate(GiftBoxScreens.GiftBoxMotion.create(it))
        },
        moveToWebView = { url ->
            navController.navigate(CommonScreen.WebView.create(url))
        }
    )
}