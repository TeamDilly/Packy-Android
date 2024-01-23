package com.packy.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.navigation.PackyNavHost

@Composable
fun RootCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RootComposeViewModel = hiltViewModel()
) {
    val startDestination = if (viewModel.checkUserStatusOnAppEntry() == UserState.REGISTERED) {
        CreateBoxRoute.CREATE_BOX_NAV_GRAPH
    } else {
        OnboardingRoute.ONBOARDING_NAV_GRAPH
    }
    PackyNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
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