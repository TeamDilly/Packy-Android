package com.packy.createbox.createboax.addyourmusic

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoIntent
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R
import com.packy.lib.ext.extractYouTubeVideoId
import com.packy.mvi.ext.emitMviIntent

@Composable
fun CreateBoxYourMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: (Boolean) -> Unit,
    saveMusic: (String) -> Unit,
    viewModel: CreateBoxYourMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxYourMusicEffect.CloseBottomSheet -> closeBottomSheet(false)
                CreateBoxYourMusicEffect.MoveToBack -> navController.popBackStack()
                is CreateBoxYourMusicEffect.SaveMusic -> {
                    saveMusic(effect.youtubeLink)
                    closeBottomSheet(false)
                    navController.popBackStack(
                        route = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC,
                        inclusive = true
                    )
                }
            }
        }
    }
    BackHandler(true) {
        viewModel.emitIntent(CreateBoxYourMusicIntent.OnBackClick)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
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
                .fillMaxSize(),
        ) {
            BottomSheetTitle(
                BottomSheetTitleContent(
                    title = Strings.CREATE_BOX_ADD_YOUR_MUSIC_TITLE,
                    description = Strings.CREATE_BOX_ADD_YOUR_MUSIC_DESCRIPTION,
                )
            )
            Spacer(height = 32.dp)
            val youtubeVideoId = extractYouTubeVideoId(uiState.youtubeLink)
            if (uiState.validationYoutubeLink == true && youtubeVideoId != null) {
                YoutubeView(
                    modifier = Modifier
                        .padding(horizontal = 20.dp),
                    youtubeVideoId = youtubeVideoId,
                    close = viewModel::emitIntent,
                )
            } else {
                YoutubeLinkForm(
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                    link = uiState.youtubeLink,
                    onLinkChange = viewModel::emitIntent,
                    isFailUrl = viewModel.currentState.validationYoutubeLink == false,
                    onConfirmClick = viewModel::emitIntentThrottle
                )
            }
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                style = buttonStyle.large.black,
                text = Strings.SAVE,
                enabled = viewModel.currentState.validationYoutubeLink == true
            ) {
                viewModel.emitIntentThrottle(CreateBoxYourMusicIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun YoutubeView(
    modifier: Modifier = Modifier,
    youtubeVideoId: String,
    close: emitMviIntent<CreateBoxYourMusicIntent>,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        YoutubePlayer(
            modifier = Modifier
                .padding(all = 4.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp)),
            videoId = youtubeVideoId,
            youtubeState = YoutubeState.INIT,
            stateListener = {}
        )
        PackyCloseIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd),
            style = closeIconButtonStyle.medium.black
        ) {
            close(CreateBoxYourMusicIntent.OnYoutubeCancelClick)
        }
    }
}

@Composable
private fun YoutubeLinkForm(
    modifier: Modifier = Modifier,
    link: String = "",
    onLinkChange: emitMviIntent<CreateBoxYourMusicIntent>,
    isFailUrl: Boolean = false,
    onConfirmClick: emitMviIntent<CreateBoxYourMusicIntent>
) {
    Row(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            PackyTextField(
                value = link,
                onValueChange = {
                    onLinkChange(CreateBoxYourMusicIntent.OnYoutubeLinkChange(it))
                },
                singleLine = true,
                placeholder = Strings.CREATE_BOX_ADD_MUSIC_PLACE_HOLDER,
                showTrailingIcon = true
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
            onConfirmClick(CreateBoxYourMusicIntent.OnValidateCheckYoutubeLink)
        }
    }
}
