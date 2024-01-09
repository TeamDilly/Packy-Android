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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.packy.core.widget.topbar.PackyTopBar
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
        2
    })

    LaunchedEffect(null) {
        viewModel.effect.collect {
            when (it) {
                OnboardingEffect.GoToLoginScreenEffect -> navController.navigate(OnboardingRoute.LOGIN)
            }
        }
    }

    LaunchedEffect(key1 = uiState.currentPage) {
        pagerState.animateScrollToPage(uiState.currentPage)
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .showLogo()
                .endTextButton(Strings.SKIP) {
                    viewModel.emitIntent(OnboardingIntent.OnSkipButtonClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
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
                state = pagerState,
                userScrollEnabled = false
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
                text = if (uiState.currentPage == OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE) {
                    Strings.START
                } else {
                    Strings.NEXT
                }
            ) {
                if (uiState.currentPage == OnboardingViewModel.MAX_ONBOARDING_PAGE_SIZE) {
                    viewModel.emitIntent(OnboardingIntent.OnStartButtonClick)
                } else {
                    viewModel.emitIntent(OnboardingIntent.OnNextButtonClick)
                }
            }
            Spacer(modifier = Modifier.height(37.dp))
        }
    }
}