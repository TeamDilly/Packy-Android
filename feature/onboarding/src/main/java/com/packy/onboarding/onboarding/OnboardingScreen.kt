package com.packy.onboarding.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.button.PackyButton
import com.packy.core.widget.button.buttonStyle
import com.packy.core.widget.indicator.PackyIndicator
import com.packy.core.widget.topbar.PackyTopBar
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingRoute

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE + 1
    })

    LaunchedEffect(null) {
        viewModel.effect.collect {
            when (it) {
                OnboardingEffect.GoToLoginScreenEffect -> navController.navigate(OnboardingRoute.LOGIN)
            }
        }
    }

    LaunchedEffect(key1 = uiState.currentPage) {
        if (uiState.currentPage != pagerState.currentPage) {
            pagerState.animateScrollToPage(uiState.currentPage)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.emitIntent(OnboardingIntent.OnScrollPager(page))
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .endTextButton(Strings.SKIP) {
                    viewModel.emitIntentThrottle(OnboardingIntent.OnSkipButtonClick)
                }
                .build()
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            OnboardingPager(pagerState, uiState)
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                PackyIndicator(
                    modifier = Modifier,
                    pagerState = pagerState
                )
                Spacer(119.dp)
                PackyButton(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    style = buttonStyle.large.purple,
                    text = if (uiState.currentPage == OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE) {
                        Strings.START
                    } else {
                        Strings.NEXT
                    }
                ) {
                    if (uiState.currentPage == OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE) {
                        viewModel.emitIntentThrottle(OnboardingIntent.OnStartButtonClick)
                    } else {
                        viewModel.emitIntentThrottle(OnboardingIntent.OnNextButtonClick)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun OnboardingPager(
    pagerState: PagerState,
    uiState: OnboardingState
) {
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(66.dp)
            Text(
                text = uiState.getTitle(),
                modifier = Modifier.padding(horizontal = 24.dp),
                style = PackyTheme.typography.heading01,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(54.dp))
            Image(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                painter = painterResource(R.drawable.packy_logo),
                contentDescription = "dummyLogo"
            )
            Spacer(225.dp)
        }
    }
}