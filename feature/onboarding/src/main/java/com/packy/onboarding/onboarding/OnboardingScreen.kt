package com.packy.onboarding.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.indicator.PackyIndicator
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.feature.core.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.ONBOARDING)
    )

    val uiState by viewModel.uiState.collectAsState()

    val pagerState = rememberPagerState(pageCount = {
        OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE + 1
    })

    LaunchedEffect(null) {
        viewModel.effect.collect {
            when (it) {
                OnboardingEffect.NavLoginScreenEffect -> navController.navigate(com.packy.onboarding.navigation.OnboardingScreen.Login.name)
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
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
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
            OnboardingPager(
                modifier = modifier
                    .align(Alignment.Center)
                    .padding(bottom = 40.dp),
                pagerState = pagerState,
            )
            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                PackyIndicator(
                    modifier = Modifier,
                    pagerState = pagerState
                )
                Spacer(103.dp)
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
    modifier: Modifier = Modifier,
    pagerState: PagerState,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState,
    ) {
        val title: String = when (it) {
            0 -> Strings.ONBOARDING_TITLE_1
            1 -> Strings.ONBOARDING_TITLE_2
            else -> Strings.ONBOARDING_TITLE_1
        }
        val image: Int = when (it) {
            0 -> R.drawable.onboarding_img_1
            1 -> R.drawable.onboarding_img_2
            else -> R.drawable.onboarding_img_1
        }
        Box(
            modifier = Modifier
        ) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.TopCenter),
                style = PackyTheme.typography.heading01,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp),
                painter = painterResource(image),
                contentDescription = "dummyLogo"
            )
        }
    }
}