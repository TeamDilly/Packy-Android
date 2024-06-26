package com.packy.onboarding.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.common.authenticator.KakaoLoginController
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings.LOGIN_TITLE
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingScreen

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    loggedIn: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.LOGIN)
    )

    val uiState by viewModel.uiState.collectAsState()
    val kakaoLoginController = KakaoLoginController()
    val context = LocalContext.current

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.KakaoLogin -> {
                    kakaoLoginController.login(context, viewModel::kakoLogin)
                }

                LoginEffect.KakaoLoginSuccess -> loggedIn()
                is LoginEffect.KakaoLoginSuccessNotUser -> navController.navigate(OnboardingScreen.SignupNickName.create(effect.nickname))
                LoginEffect.KakaoLoginFail -> TODO()
            }
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.packy_logo),
                contentDescription = "Packy Logo"
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = LOGIN_TITLE,
                style = PackyTheme.typography.body03,
                color = PackyTheme.color.gray900
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.login_button_kakao),
                contentDescription = "Kakao Login Button",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clickable {
                        viewModel.emitIntentThrottle(LoginIntent.OnKakaoLoginButtonClick)
                    }
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}