package com.packy.createbox.createboax.addyourmusic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R

@Composable
fun CreateBoxYourMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxYourMusicViewModel = hiltViewModel()
) {
    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxYourMusicEffect.CloseBottomSheet -> closeBottomSheet()
                CreateBoxYourMusicEffect.MoveToAddPhoto -> navController.navigate(
                    CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PHOTO
                )

                CreateBoxYourMusicEffect.MoveToBack -> navController.popBackStack()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(height = 12.dp)
        PackyTopBar.Builder()
            .startIconButton(icon = R.drawable.arrow_left) {
                viewModel.emitIntent(CreateBoxYourMusicIntent.OnBackClick)
            }
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxYourMusicIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
        ) {
            Text(
                text = Strings.CREATE_BOX_ADD_MUSIC_TITLE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900
            )
            Spacer(height = 4.dp)
            Text(
                text = Strings.CREATE_BOX_ADD_MUSIC_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
            Spacer(height = 32.dp)
            YoutubeLinkForm(
                link = viewModel.currentState.youtubeLink,
                onLinkChange = {
                    viewModel.emitIntent(
                        CreateBoxYourMusicIntent.OnYoutubeLinkChange(
                            it
                        )
                    )
                },
                isFailUrl = viewModel.currentState.validationYoutubeLink == false
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.black,
                text = Strings.SAVE,
                enabled = viewModel.currentState.validationYoutubeLink == true
            ) {
                viewModel.emitIntent(CreateBoxYourMusicIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun YoutubeLinkForm(
    link: String = "",
    onLinkChange: (String) -> Unit = {},
    isFailUrl: Boolean = false,
) {
    Row {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            PackyTextField(
                value = link,
                onValueChange = onLinkChange,
                placeholder = Strings.CREATE_BOX_ADD_MUSIC_PLACE_HOLDER,
            )
            if (isFailUrl) {
                Spacer(height = 4.dp)
                Text(
                    text = Strings.CREATE_BOX_ADD_MUSIC_FAIL_URL,
                    style = PackyTheme.typography.body06,
                    color = PackyTheme.color.errorText
                )
            }
        }
        Spacer(width = 8.dp)
        PackyButton(
            modifier = Modifier
                .width(100.dp),
            style = buttonStyle.medium.black,
            text = Strings.CONFIRM
        ) {
        }
    }
}
