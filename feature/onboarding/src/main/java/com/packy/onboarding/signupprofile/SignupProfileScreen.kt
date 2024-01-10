package com.packy.onboarding.signupprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.SIGNUP_PROFILE
import com.packy.core.widget.button.PackyButton
import com.packy.core.widget.button.buttonStyle
import com.packy.core.widget.topbar.PackyTopBar
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.onboarding.signupnickname.SignupNickNameEffect

@Composable
fun SignupProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SignupProfileEffect.NavTermsAgreementEffect -> navController.navigate(
                    OnboardingRoute.TERMS_AGREEMENT
                )
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
                modifier = Modifier.fillMaxWidth(),
                text = SIGNUP_PROFILE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900,
                textAlign = TextAlign.Start
            )
            Spacer(80.dp)
            ProfileImages()
            Spacer(1f)
            PackyButton(style = buttonStyle.large.purple, text = Strings.SAVE) {
                viewModel.emitIntentThrottle(SignupProfileIntent.OnSaveButtonClick)
            }
            Spacer(16.dp)
        }
    }
}

@Composable
private fun ColumnScope.ProfileImages() {
    Image(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.packy_logo),
        contentDescription = "Selected Profile Image"
    )
    Spacer(40.dp)
    Row {
        repeat(4) {
            Image(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.packy_logo),
                contentDescription = "Profile Image"
            )
        }
    }
}