package com.packy.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.giftbox.navigation.GiftBoxRoute
import com.example.home.navigation.HomeRoute
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.feature.main.R
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.navigation.MainRoute
import com.packy.root.navigation.PackyNavHost

@Composable
fun RootCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RootComposeViewModel = hiltViewModel()
) {
    val kakaoLinkScheme = stringResource(id = R.string.kakao_link_scheme)
    PackyNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute.LAUNCH_NAV_GRAPH,
        logout = {
            viewModel.logout()
            navController.navigate(MainRoute.LAUNCH_NAV_GRAPH){
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        },
        moveToHomeClear = {
            navController.navigate(HomeRoute.HOME_NAV_GRAPH) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        },
        moveToBoxDetail = {
            navController.navigate(GiftBoxRoute.getGiftBoxRootRoute(it))
        },
        moveToCreateBox = {
            navController.navigate(CreateBoxRoute.CREATE_BOX_NAV_GRAPH)
        },
        kakaoLinkScheme = kakaoLinkScheme,
    )
}