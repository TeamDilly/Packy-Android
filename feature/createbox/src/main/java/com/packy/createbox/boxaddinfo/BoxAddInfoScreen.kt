package com.packy.createbox.boxaddinfo

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxAddInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeCreateBox: () -> Unit,
    viewModel: BoxAddInfoViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.BOX_ADD_INFO)
    )
    val uiState by viewModel.uiState.collectAsState()
    val isKeyboardOpen by keyboardAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val senderFocus = remember {
        FocusRequester()
    }
    val receiverFocus = remember {
        FocusRequester()
    }

    BackHandler(true) {
        closeCreateBox()
    }

    LaunchedEffect(null) {
        viewModel.getLetterSenderReceiver()
        viewModel.effect.collect { effect ->
            when (effect) {
                BoxAddInfoEffect.MoveToBack -> closeCreateBox()
                BoxAddInfoEffect.SaveBoxInfo -> {
                    keyboardController?.hide()
                    navController.navigate(CreateBoxRoute.BOX_CHOICE)
                }
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
                    .requiredHeightIn(min = 124.dp)
                    .background(
                        color = PackyTheme.color.gray100,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                AddInfoForm(
                    text = uiState.letterSenderReceiver.receiver,
                    title = Strings.BOX_ADD_INFO_RECEIVER,
                    textFieldFocus = receiverFocus,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = { senderFocus.requestFocus() },
                    )
                ) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeReceiver(it))
                }
                DottedDivider(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                    thickness = 1.dp,
                )
                AddInfoForm(
                    text = uiState.letterSenderReceiver.sender,
                    title = Strings.BOX_ADD_INFO_SENDER,
                    textFieldFocus = senderFocus,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    )
                ) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeSender(it))
                }
            }
            Spacer(height = 20.dp)
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .imePadding()
                    .fillMaxWidth(),
                enabled = uiState.isSavable(),
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
private fun BoxAddInfoTitle() {
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
    textFieldFocus: FocusRequester,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
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
            modifier = Modifier,
            focusRequester = textFieldFocus,
            value = text,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            placeholder = Strings.BOX_ADD_INFO_PLACEHOLDER,
            textAlign = TextAlign.End,
            onValueChange = onChange,
            singleLine = true,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BoxAddInfoTitlePreview() {
    PackyTheme {
        BoxAddInfoTitle()
    }
}

@Composable
@Preview(showBackground = true)
private fun AddInfoFormPreview() {
    PackyTheme {
        AddInfoForm(
            text = "홍길동",
            title = "받는 사람",
            textFieldFocus = FocusRequester(),
        ) { }
    }
}