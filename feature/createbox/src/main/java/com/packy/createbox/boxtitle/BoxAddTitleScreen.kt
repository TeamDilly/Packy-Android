package com.packy.createbox.boxtitle

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.feature.core.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxAddTitleScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxAddTitleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(null) {
        viewModel.initBoxTitle()
        viewModel.effect.collect { effect ->
            when (effect) {
                BoxAddTitleEffect.MoveToBack -> {
                    navController.popBackStack()
                }

                is BoxAddTitleEffect.SaveBoxTitle -> {
                    keyboardController?.hide()
                    if (effect.showMotion) {
                        navController.navigate(CreateBoxRoute.getBoxShareMotionRoute(effect.boxId))
                    } else {
                        navController.navigate(CreateBoxRoute.BOX_SHARE)
                    }
                }
            }
        }
    }

    BackHandler(true) {
        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    viewModel.emitIntent(BoxAddTitleIntent.OnBackClick)
                }
                .build(
                    modifier = Modifier.padding(top = 12.dp)
                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .imePadding()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Strings.CREATE_BOX_ADD_TITLE_TITLE,
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900
            )
            Spacer(height = 8.dp)
            Text(
                text = Strings.CREATE_BOX_ADD_TITLE_DESCRIPTION,
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray600
            )
            Spacer(height = 40.dp)
            PackyTextField(
                value = uiState.boxTitle,
                placeholder = Strings.INPUT_MAX_LENGTH_12,
                onValueChange = {
                    viewModel.emitIntent(BoxAddTitleIntent.OnTitleChange(it))
                },
                maxLines = 1,
                maxValues = 12,
            )
            Spacer(1f)
            PackyButton(
                enabled = uiState.boxAllReady,
                style = buttonStyle.large.black,
                text = Strings.NEXT,
                onClick = {
                    viewModel.emitIntent(BoxAddTitleIntent.MoveToNext(uiState.boxTitle))
                }
            )
            Spacer(height = 16.dp)
        }
    }
}