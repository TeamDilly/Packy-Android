package com.packy.createbox.createboax.addlatter

import android.graphics.Color.parseColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.snackbar.PackySnackBarHost
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Constant
import com.packy.core.values.Strings
import com.packy.core.values.Strings.CREATE_BOX_ADD_Letter_OVER_FLOW_Letter_TEXT
import com.packy.createbox.createboax.addLetter.CreateBoxLetterEffect
import com.packy.createbox.createboax.addLetter.CreateBoxLetterIntent
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.domain.model.createbox.LetterEnvelope
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.launch

@Composable
fun CreateBoxLetterScreen(
    modifier: Modifier = Modifier,
    closeBottomSheet: () -> Unit,
    saveLetter: (Int, String, String) -> Unit,
    viewModel: CreateBoxLetterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackBarState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackBarVisible: Boolean = false

    LaunchedEffect(viewModel) {
        viewModel.getLetterEnvelope()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxLetterEffect.CloseBottomSheet -> {
                    closeBottomSheet()
                }

                is CreateBoxLetterEffect.SaveLetter -> {
                    saveLetter(
                        effect.envelopId,
                        effect.envelopUri,
                        effect.LetterText
                    )
                    closeBottomSheet()
                }

                CreateBoxLetterEffect.OverFlowLetterText -> {
                    scope.launch {
                        if (!snackBarVisible) {
                            snackBarVisible = true
                            snackBarState.showSnackbar(
                                message = CREATE_BOX_ADD_Letter_OVER_FLOW_Letter_TEXT,
                                duration = SnackbarDuration.Short
                            ).let {
                                snackBarVisible = false
                            }
                        }
                    }
                }
            }
        }
    }
    Scaffold(
        snackbarHost = {
            PackySnackBarHost(
                snackBarHostState = snackBarState,
            )
        },
    ) { innerPadding ->
        // Scaffold를 스낵바 용으로 사용하고 있어서 innerPadding를 사용하지 않음.
        val padding = innerPadding
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(height = 12.dp)
            PackyTopBar.Builder()
                .endIconButton(icon = R.drawable.cancle) {
                    viewModel.emitIntent(CreateBoxLetterIntent.OnCloseClick)
                }
                .build()
            Spacer(height = 9.dp)
            BottomSheetTitle(
                BottomSheetTitleContent(
                    title = Strings.CREATE_BOX_ADD_Letter_TITLE,
                    description = Strings.CREATE_BOX_ADD_Letter_DESCRIPTION,
                )
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxSize(),
            ) {

                Spacer(height = 32.dp)
                LetterForm(
                    uiState.letterText,
                    uiState.getLetterEnvelope(),
                    viewModel::emitIntent,
                )
                Spacer(height = 16.dp)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(78.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(uiState.envelopeList.size) { index ->
                        Envelope(
                            isSelected = uiState.envelopeId == uiState.envelopeList[index].id,
                            envelope = uiState.envelopeList[index],
                            onClick = viewModel::emitIntent
                        )
                    }
                }
                Spacer(1f)
                PackyButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    style = buttonStyle.large.black,
                    text = Strings.SAVE,
                    enabled = uiState.letterText.isNotEmpty()
                ) {
                    viewModel.emitIntentThrottle(CreateBoxLetterIntent.OnSaveClick)
                }
                Spacer(16.dp)
            }
        }
    }
}

@Composable
private fun LetterForm(
    text: String,
    envelope: LetterEnvelope?,
    onValueChange: emitMviIntent<CreateBoxLetterIntent>,
) {
    Box {
        PackyTextField(
            value = text,
            onValueChange = { onValueChange(CreateBoxLetterIntent.ChangeLetterText(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(
                    width = 4.dp,
                    color = envelope?.borderColorCode
                        ?.let { Color(parseColor("#${envelope.borderColorCode}")) }
                        ?: PackyTheme.color.gray200,
                    shape = RoundedCornerShape(16.dp)
                ),
            textFieldColor = PackyTheme.color.gray100,
            placeholder = Strings.CREATE_BOX_ADD_Letter_PLACEHOLDER,
            textAlign = TextAlign.Center,
            maxLines = Constant.MAX_Letter_LINES,
        )
        Text(
            modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    end = 16.dp
                )
                .align(Alignment.BottomEnd),
            text = "${text.length}/${Constant.MAX_Letter_TEXT}",
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.gray600,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Envelope(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    envelope: LetterEnvelope,
    onClick: emitMviIntent<CreateBoxLetterIntent>,
) {
    val border = if (isSelected) {
        3.dp
    } else {
        0.dp
    }

    Surface(
        modifier = modifier
            .height(78.dp)
            .width(78.dp)
            .clickableWithoutRipple {
                onClick(CreateBoxLetterIntent.ChangeEnvelope(envelope.id))
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            border,
            PackyTheme.color.gray900
        ),
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            model = envelope.imgUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}