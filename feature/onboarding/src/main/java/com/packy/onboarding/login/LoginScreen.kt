package com.packy.onboarding.login

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.LOGIN_TITLE
import com.packy.core.widget.topbar.PackyTopBar
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingRoute

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                LoginEffect.KakaoLoginFail ->{
                  // FIXME : 로그인 실패 처리
                }
                LoginEffect.KakaoLoginSuccess -> navController.navigate(OnboardingRoute.SIGNUP_NICKNAME)
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