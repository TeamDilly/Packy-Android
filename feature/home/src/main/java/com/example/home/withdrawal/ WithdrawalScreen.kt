package com.example.home.withdrawal

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.WITHDRAWAL_BUTTON
import com.packy.core.values.Strings.WITHDRAWAL_DIALOG_TITLE
import com.packy.core.widget.dotted.DottedText
import com.packy.feature.core.R

@Composable
fun WithdrawalScreen(
    modifier: Modifier = Modifier,
    logout: () -> Unit,
    navController: NavController,
    viewModel: WithdrawalViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showWithdrawalDialog by remember { derivedStateOf { uiState.showWithdrawalDialog } }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                WithdrawalEffect.MoveToBack -> navController.popBackStack()
                WithdrawalEffect.Logout -> logout()
            }
        }
    }

    if (showWithdrawalDialog) {
        PackyDialog(
            title = WITHDRAWAL_DIALOG_TITLE,
            dismiss = Strings.CONFIRM,
            confirm = Strings.CANCEL,
            onConfirm = { viewModel.emitIntentThrottle(WithdrawalIntent.OnCloseWithdrawalDialogClick) },
            onDismiss = { viewModel.emitIntentThrottle(WithdrawalIntent.OnWithdrawalClick) },
            backHandler = { viewModel.emitIntentThrottle(WithdrawalIntent.OnCloseWithdrawalDialogClick) }
        )
    }

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    viewModel.emitIntentThrottle(WithdrawalIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(40.dp)
            Text(
                text = Strings.WITHDRAWAL_TITLE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900
            )
            Spacer(28.dp)
            DottedText(
                text = { Strings.WITHDRAWAL_DESCRIPTION_1 },
                style = PackyTheme.typography.body02,
                color = PackyTheme.color.gray800
            )
            Spacer(height = 8.dp)
            DottedText(
                text = { Strings.WITHDRAWAL_DESCRIPTION_2 },
                style = PackyTheme.typography.body02,
                color = PackyTheme.color.gray800
            )
            Spacer(height = 8.dp)
            DottedText(
                text = { Strings.WITHDRAWAL_DESCRIPTION_3 },
                style = PackyTheme.typography.body02,
                color = PackyTheme.color.gray800
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.black,
                text = WITHDRAWAL_BUTTON
            ) {
                viewModel.emitIntentThrottle(WithdrawalIntent.OnShowWithdrawalDialogClick)
            }
            Spacer(height = 16.dp)
        }
    }
}