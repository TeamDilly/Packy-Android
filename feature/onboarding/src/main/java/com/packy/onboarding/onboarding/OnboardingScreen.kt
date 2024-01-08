package com.packy.onboarding.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.theme.PackyTheme
import com.packy.onboarding.navigation.OnboardingRoute

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    LaunchedEffect(null) {
        viewModel.effect.collect {
            when (it) {
                OnboardingEffect.GoToLoginScreenEffect -> navController.navigate(OnboardingRoute.LOGIN)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PackyTheme.color.purple500)
    ) {

    }
}