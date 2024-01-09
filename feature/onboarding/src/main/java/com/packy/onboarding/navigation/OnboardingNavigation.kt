package com.packy.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.packy.core.animations.PaginationAnimation
import com.packy.onboarding.login.LoginScreen
import com.packy.onboarding.onboarding.OnboardingScreen
import com.packy.onboarding.signupnickname.SignupNickNameScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = OnboardingRoute.ONBOARDING,
        route = OnboardingRoute.ONBOARDING_NAV_GRAPH,
    ) {
        composable(
            route = OnboardingRoute.ONBOARDING,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }, exitTransition = {
                PaginationAnimation.slidOutToBottom()
            }
        ) {
            OnboardingScreen(navController = navController)
        }
        composable(
            route = OnboardingRoute.LOGIN,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }, exitTransition = {
                PaginationAnimation.slidOutToEnd()
            }
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = OnboardingRoute.SIGNUP_NICKNAME,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }, exitTransition = {
                PaginationAnimation.slidOutToEnd()
            }
        ) {
            SignupNickNameScreen(navController = navController)
        }
    }
}

object OnboardingRoute {
    const val ONBOARDING_NAV_GRAPH = "onboardingNavGraph"

    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val SIGNUP_NICKNAME = "signupNickName"
    const val SIGNUP_PROFILE_IMAGE = "signupProfileImage"
    const val TERMS_AGREEMENT = "termsAgreement"
}