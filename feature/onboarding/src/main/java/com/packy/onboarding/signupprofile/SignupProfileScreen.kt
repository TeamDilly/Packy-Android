package com.packy.onboarding.signupprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.SIGNUP_PROFILE
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import com.packy.onboarding.navigation.OnboardingRoute

@Composable
fun SignupProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupProfileViewModel = hiltViewModel()
) {
    val haptic = LocalHapticFeedback.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(null) {
        viewModel.initProfile()
        viewModel.effect.collect { effect ->
            when (effect) {
                SignupProfileEffect.NavTermsAgreementEffect -> navController.navigate(
                    OnboardingRoute.TERMS_AGREEMENT
                )

                SignupProfileEffect.ProfileChangeHapticEffect -> {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }

                SignupProfileEffect.MoveToBack -> navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    viewModel.emitIntentThrottle(SignupProfileIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(8.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = SIGNUP_PROFILE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900,
                textAlign = TextAlign.Start
            )
            Spacer(80.dp)
            ProfileImages(
                onProfileImageClick = {
                    viewModel.emitIntentThrottle(it)
                },
                uiState = uiState
            )
            Spacer(1f)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                style = buttonStyle.large.purple,
                text = Strings.SAVE
            ) {
                viewModel.emitIntentThrottle(SignupProfileIntent.OnSaveButtonClick)
            }
            Spacer(16.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ColumnScope.ProfileImages(
    modifier: Modifier = Modifier,
    onProfileImageClick: emitMviIntent<SignupProfileIntent>,
    uiState: SignupProfileState,
) {
    GlideImage(
        modifier = modifier
            .background(PackyTheme.color.gray100, shape = CircleShape)
            .size(160.dp)
            .clip(CircleShape),
        model = uiState.selectedProfile?.imageUrl,
        contentDescription = "Selected Profile Image"
    )
    Spacer(40.dp)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(uiState.profiles) { profile ->
            GlideImage(
                modifier = Modifier
                    .background(PackyTheme.color.gray100, shape = CircleShape)
                    .size(60.dp)
                    .clip(CircleShape)
                    .clickableWithoutRipple {
                        onProfileImageClick(
                            SignupProfileIntent.OnChangeProfile(
                                profile,
                            )
                        )
                    },
                model = profile.imageUrl,
                contentDescription = "Profile Image"
            )
        }
    }
}