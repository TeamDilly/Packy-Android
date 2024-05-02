package com.packy.onboarding.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.packy.core.animations.asPagingComposable
import com.packy.core.animations.asRootComposable
import com.packy.core.navigiation.NavScreens
import com.packy.core.navigiation.replaceArguments
import com.packy.onboarding.login.LoginScreen
import com.packy.onboarding.navigation.OnboardingRouteArgs.NICKNAME
import com.packy.onboarding.onboarding.OnboardingScreen
import com.packy.onboarding.signupnickname.SignupNickNameScreen
import com.packy.onboarding.signupprofile.SignupProfileScreen
import com.packy.onboarding.termsagreement.TermsAgreementScreen

fun NavGraphBuilder.onboardingNavGraph(
    navController: NavHostController,
    moveToHomeClear: () -> Unit
) {
    navigation(
        startDestination = OnboardingScreen.Onboarding.name,
        route = OnboardingScreen.OnboardingNavGraph.name,
    ) {
        asRootComposable(
            route = OnboardingScreen.Onboarding.name,
        ) {
            OnboardingScreen(navController = navController)
        }
        asPagingComposable(
            route = OnboardingScreen.Login.name,
        ) {
            LoginScreen(
                navController = navController,
                loggedIn = moveToHomeClear
            )
        }
        asPagingComposable(
            route = OnboardingScreen.SignupNickName.name,
            arguments = listOf(
                navArgument(NICKNAME) {
                    nullable = true
                    type = androidx.navigation.NavType.StringType
                    defaultValue = null
                }
            )
        ) {
            SignupNickNameScreen(
                navController = navController,
            )
        }
        asPagingComposable(
            route = OnboardingScreen.SignupProfile.name,
        ) {
            SignupProfileScreen(navController = navController)
        }
        asPagingComposable(
            route = OnboardingScreen.TermsAgreement.name,
        ) {
            TermsAgreementScreen(
                navController = navController,
                signUp = moveToHomeClear
            )
        }
    }
}

sealed class OnboardingScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) : NavScreens(_route = route, _navArguments = navArguments) {
    data object OnboardingNavGraph : OnboardingScreen(route = "onboardingNavGraph")
    data object Onboarding : OnboardingScreen(route = "onboarding")
    data object Login : OnboardingScreen(route = "login")

    data object SignupNickName : OnboardingScreen(
        route = "signupNickName",
        navArguments = listOf(
            navArgument(NICKNAME) {
                type = androidx.navigation.NavType.StringType
                defaultValue = ""
            }
        )
    ) {
        fun create(nickname: String?) = name.replaceArguments(navArguments.first(), nickname ?: "")
    }

    data object SignupProfile : OnboardingScreen(route = "signupProfile")
    data object TermsAgreement : OnboardingScreen(route = "termsAgreement")
}

object OnboardingRouteArgs {
    const val NICKNAME = "nickname"
}