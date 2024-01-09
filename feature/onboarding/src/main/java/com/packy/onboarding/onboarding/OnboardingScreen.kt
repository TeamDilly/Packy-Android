package com.packy.onboarding.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.button.PackyButton
import com.packy.core.widget.button.buttonStyle
import com.packy.core.widget.indicator.PackyIndicator
import com.packy.onboarding.navigation.OnboardingRoute

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = {
        2
    })

    LaunchedEffect(null) {
        viewModel.effect.collect {
            when (it) {
                OnboardingEffect.GoToLoginScreenEffect -> navController.navigate(OnboardingRoute.LOGIN)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            Strings.ONBOARDING_TITLE,
            modifier = Modifier.padding(horizontal = 51.dp),
            style = PackyTheme.typography.heading01,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(44.dp))
        HorizontalPager(
            modifier = Modifier.size(300.dp),
            state = pagerState
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(com.packy.feature.core.R.drawable.packy_logo),
                contentDescription = "dummyLogo"
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        PackyIndicator(pagerState = pagerState)
        Spacer(modifier = Modifier.weight(1f))
        PackyButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            style = buttonStyle.large.purple,
            text = Strings.START
        ) {
            viewModel.emitIntent(OnboardingIntent.OnStartButtonClick)
        }
        Spacer(modifier = Modifier.height(37.dp))
    }
}