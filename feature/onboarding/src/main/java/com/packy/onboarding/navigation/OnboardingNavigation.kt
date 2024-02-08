package com.packy.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.onboarding.login.LoginScreen
import com.packy.onboarding.onboarding.OnboardingScreen
import com.packy.onboarding.signupnickname.SignupNickNameScreen
import com.packy.onboarding.signupprofile.SignupProfileScreen
import com.packy.onboarding.termsagreement.TermsAgreementScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
    moveToHomeClear: () -> Unit
) {
    navigation(
        startDestination = OnboardingRoute.ONBOARDING,
        route = OnboardingRoute.ONBOARDING_NAV_GRAPH,
    ) {
        asRootComposable(
            route = OnboardingRoute.ONBOARDING,
        ) {
            OnboardingScreen(navController = navController)
        }
        asPagingComposable(
            route = OnboardingRoute.LOGIN,
        ) {
            LoginScreen(
                navController = navController,
                loggedIn = moveToHomeClear
            )
        }
        asPagingComposable(
            route = OnboardingRoute.SIGNUP_NICKNAME,
        ) {
            SignupNickNameScreen(navController = navController)
        }
        asPagingComposable(
            route = OnboardingRoute.SIGNUP_PROFILE,
        ) {
            SignupProfileScreen(navController = navController)
        }
        asPagingComposable(
            route = OnboardingRoute.TERMS_AGREEMENT,
        ) {
            TermsAgreementScreen(
                navController = navController,
                signUp = moveToHomeClear
            )
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