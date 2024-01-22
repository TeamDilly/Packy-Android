package com.packy.createbox.boxaddinfo

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.common.keyboardAsState
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.dotted.DottedDivider
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.feature.core.R

@Composable
fun BoxAddInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxAddInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BoxAddInfoEffect.MoveToBack -> navController.popBackStack()
                BoxAddInfoEffect.SaveBoxInfo -> navController.navigate(CreateBoxRoute.BOX_CHOICE)
            }
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    viewModel.emitIntent(BoxAddInfoIntent.OnBackClick)
                }
                .build(
                    modifier = Modifier.padding(top = 12.dp)
                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(height = 24.dp)
            AnimatedVisibility(!isKeyboardOpen) {
                BoxAddInfoTitle()
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(
                        color = PackyTheme.color.gray100,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                AddInfoForm(
                    text = uiState.toName,
                    title = Strings.BOX_ADD_INFO_SENDER
                ) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeToName(it))
                }
                DottedDivider(modifier = Modifier.padding(vertical = 16.dp))
                AddInfoForm(
                    text = uiState.toName,
                    title = Strings.BOX_ADD_INFO_RECEIVER
                ) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeFromName(it))
                }
            }
            Spacer(height = 20.dp)
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .imePadding()
                    .fillMaxWidth(),
                style = buttonStyle.large.black,
                text = Strings.NEXT,
                onClick = {
                    viewModel.emitIntent(BoxAddInfoIntent.OnSaveButtonClick)
                }
            )
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun ColumnScope.BoxAddInfoTitle() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = Strings.BOX_ADD_INFO_TITLE,
            style = PackyTheme.typography.heading01.copy(textAlign = TextAlign.Center),
            color = PackyTheme.color.gray900
        )
        Spacer(height = 8.dp)
        Text(
            text = Strings.BOX_ADD_INFO_DESCRIPTION,
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.gray600
        )
        Spacer(height = 40.dp)
    }
}

@Composable
private fun AddInfoForm(
    text: String,
    title: String,
    onChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(width = 20.dp)
        Text(
            text = title,
            style = PackyTheme.typography.body02,
            color = PackyTheme.color.gray900
        )
        Spacer(1f)
        PackyTextField(
            value = text,
            placeholder = Strings.BOX_ADD_INFO_PLACEHOLDER,
            textAlign = TextAlign.End,
            onValueChange = onChange,
        )
    }
}