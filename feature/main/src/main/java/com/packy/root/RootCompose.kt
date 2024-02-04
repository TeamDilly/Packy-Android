package com.packy.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.navigation.MainRoute
import com.packy.root.navigation.PackyNavHost

@Composable
fun RootCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RootComposeViewModel = hiltViewModel()
) {
    PackyNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute.LAUNCH_ROUTE,
        loggedIn = {
            navController.navigate(CreateBoxRoute.CREATE_BOX_NAV_GRAPH) {
                popUpTo(OnboardingRoute.ONBOARDING_NAV_GRAPH) {
                    inclusive = true
                }
            }
        },
        closeCreateBox = {
            // FIXME : 박스만들기 이탈화면 구현 필요
        }
    )
}