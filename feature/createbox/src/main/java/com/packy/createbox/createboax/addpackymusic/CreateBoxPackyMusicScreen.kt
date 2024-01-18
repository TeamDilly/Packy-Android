package com.packy.createbox.createboax.addpackymusic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.youtube.YouTubeCdPlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R

@Composable
fun CreateBoxPackyMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxPackyMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var youtubeState by remember { mutableStateOf(YoutubeState.INIT) }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxPackyMusicEffect.CloseBottomSheet -> closeBottomSheet()
                CreateBoxPackyMusicEffect.MoveToAddPhoto -> navController.navigate(
                    CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PHOTO
                )

                CreateBoxPackyMusicEffect.MoveToBack -> navController.popBackStack()
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
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnBackClick)
            }
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            BottomSheetTitle(
                BottomSheetTitleContent(
                    title = Strings.CREATE_BOX_ADD_PACKY_MUSIC_TITLE,
                    description = Strings.CREATE_BOX_ADD_PACKY_MUSIC_DESCRIPTION,
                )
            )
            Spacer(1f)
            YouTubeCdPlayer(
                modifier = Modifier.size(180.dp),
                videoId = CreateBoxPackyMusicViewModel.dumyMusic[0].videoId,
                thumbnail = CreateBoxPackyMusicViewModel.dumyMusic[0].thumbnail,
                youtubeState = youtubeState,
                stateListener = {
                    youtubeState = it
                }
            )
            Spacer(1f)
            PackyButton(style = buttonStyle.large.black, text = Strings.SAVE) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnSaveClick)
            }
        }
    }
}
