package com.packy.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.packy.onboarding.onboarding.OnboardingScreen

fun NavGraphBuilder.onboardingNavGraph(
) {
    navigation(
        startDestination = OnboardingRoute.ONBOARDING,
        route = OnboardingRoute.ONBOARDING_NAV_GRAPH
    ) {
        composable(route = OnboardingRoute.ONBOARDING) {
            OnboardingScreen()
        }
    }
}

object OnboardingRoute {
    const val ONBOARDING_NAV_GRAPH = "onboarding_nav_graph"

    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val TERMS_AGREEMENT = "termsAgreement"
}