package com.packy.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.packy.core.animations.PaginationAnimation
import com.packy.onboarding.login.LoginScreen
import com.packy.onboarding.onboarding.OnboardingScreen
import com.packy.onboarding.signupnickname.SignupNickNameScreen
import com.packy.onboarding.signupprofile.SignupProfileScreen
import com.packy.onboarding.termsagreement.TermsAgreementScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
    loggedIn: () -> Unit
) {
    navigation(
        startDestination = OnboardingRoute.ONBOARDING,
        route = OnboardingRoute.ONBOARDING_NAV_GRAPH,
    ) {
        composable(
            route = OnboardingRoute.ONBOARDING,
            enterTransition = {
                PaginationAnimation.slidInTop()
            }
        ) {
            OnboardingScreen(navController = navController)
        }
        composable(
            route = OnboardingRoute.LOGIN,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ) {
            LoginScreen(navController = navController, loggedIn = loggedIn)
        }
        composable(
            route = OnboardingRoute.SIGNUP_NICKNAME,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ) {
            SignupNickNameScreen(navController = navController)
        }
        composable(
            route = OnboardingRoute.SIGNUP_PROFILE,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ) {
            SignupProfileScreen(navController = navController)
        }
        composable(
            route = OnboardingRoute.TERMS_AGREEMENT,
            enterTransition = {
                PaginationAnimation.slidInToStart()
            }
        ) {
            TermsAgreementScreen(navController = navController)
        }
    }
}

object OnboardingRoute {
    const val ONBOARDING_NAV_GRAPH = "onboardingNavGraph"

    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val SIGNUP_NICKNAME = "signupNickName"
    const val SIGNUP_PROFILE = "signupProfile"
    const val TERMS_AGREEMENT = "termsAgreement"
}