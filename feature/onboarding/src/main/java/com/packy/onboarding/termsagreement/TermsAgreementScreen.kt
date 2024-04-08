package com.packy.onboarding.termsagreement

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
import com.packy.core.checkbox.PackyCheckBox
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.feature.core.R

@Composable
fun TermsAgreementScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    signUp: () -> Unit,
    viewModel: TermsAgreementViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.SIGNUP_TERMS_AGREEMENT)
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(null) {
        viewModel.effect.collect{ effect ->
            when(effect){
                TermsAgreementEffect.MoveToBack -> navController.popBackStack()
                TermsAgreementEffect.OnSuccessSignUp -> signUp()
            }
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    viewModel.emitIntentThrottle(TermsAgreementIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(8.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = Strings.TERMS_AGREEMENT_TITLE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900,
                textAlign = TextAlign.Start
            )
            Spacer(40.dp)
            PackyCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(
                        PackyTheme.color.gray100,
                        shape = RoundedCornerShape(8.dp)
                    ),
                isSelected = uiState.isAllAllow,
                label = Strings.TERMS_AGREEMENT_ALL_ALLOW,
                iconPadding = PaddingValues(start = 12.dp),
                onClick = {
                    viewModel.emitIntentThrottle(TermsAgreementIntent.OnAllTermsAgreementClick)
                }
            )
            PackyCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isSelected = uiState.enabledTermsOfService,
                label = Strings.TERMS_AGREEMENT_SERVICE_ALLOW,
                iconPadding = PaddingValues(start = 12.dp),
                onClick = {
                    viewModel.emitIntentThrottle(TermsAgreementIntent.OnTermsOfServiceClick)
                }
            )
            PackyCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isSelected = uiState.enabledPersonalInformation,
                label = Strings.TERMS_AGREEMENT_PERSONAL_ALLOW,
                iconPadding = PaddingValues(start = 12.dp),
                onClick = {
                    viewModel.emitIntentThrottle(TermsAgreementIntent.OnPersonalInformation)
                }
            )
            PackyCheckBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                isSelected = uiState.enabledNotification,
                label = Strings.TERMS_AGREEMENT_NOTIFICATION_ALLOW,
                iconPadding = PaddingValues(start = 12.dp),
                onClick = {
                    viewModel.emitIntentThrottle(TermsAgreementIntent.OnNotification)
                }
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.purple,
                text = Strings.SAVE,
                enabled = uiState.isRequiredAllow
            ) {
                viewModel.emitIntentThrottle(TermsAgreementIntent.OnSaveButtonClick)
            }
            Spacer(height = 16.dp)
        }
    }
}