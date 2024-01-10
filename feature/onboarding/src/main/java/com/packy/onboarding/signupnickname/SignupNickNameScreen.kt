package com.packy.onboarding.signupnickname

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.packy.core.widget.button.PackyButton
import com.packy.core.widget.button.buttonStyle
import com.packy.core.widget.textfield.PackyTextField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignupNickNameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SignupNickNameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                showTrailingIcon = uiState.inputNickName?.isNotEmpty() ?: false,
                placeholder = SIGNUP_NICK_NAME_MAX_VALUE,
                maxValues = 6
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.purple,
                text = Strings.SAVE,
                enabled = uiState.isAvailableNickName
            ) {
                viewModel.emitIntentThrottle(SignupNickNameIntent.OnSaveButtonClick)
            }
            Spacer(width = 16.dp)
        }
    }
}