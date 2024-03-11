package com.packy.onboarding.signupnickname

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.SIGNUP_NICK_NAME_MAX_VALUE
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.values.Constant.MAX_NICK_NAME_LENGTH
import com.packy.onboarding.navigation.OnboardingRoute

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignupNickNameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupNickNameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SignupNickNameEffect.NavSignupProfileEffect -> navController.navigate(
                    OnboardingRoute.SIGNUP_PROFILE
                )
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .padding(innerPadding)
        ) {
            Spacer(64.dp)
            Text(
                text = Strings.SIGNUP_NICK_NAME_TITLE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900
            )
            Spacer(8.dp)
            Text(
                text = Strings.SIGNUP_NICK_NAME_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
            Spacer(40.dp)
            PackyTextField(
                value = uiState.inputNickName ?: "",
                onValueChange = {
                    viewModel.emitIntent(SignupNickNameIntent.OnChangeInputNickName(it))
                },
                showTrailingIcon = true,
                placeholder = SIGNUP_NICK_NAME_MAX_VALUE,
                maxValues = MAX_NICK_NAME_LENGTH,
                maxLines = 1
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.purple,
                text = Strings.SAVE,
                enabled = uiState.isAvailableNickName
            ) {
                keyboardController?.hide()
                viewModel.emitIntentThrottle(SignupNickNameIntent.OnSaveButtonClick)
            }
            Spacer(height = 16.dp)
        }
    }
}