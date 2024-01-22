package com.packy.createbox.createboax.addpackymusic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.youtube.YouTubeCdPlayer
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateBoxPackyMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxPackyMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(pageCount = {
        uiState.music.size
    })

    LaunchedEffect(viewModel) {
        viewModel.getSuggestionMusic()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxPackyMusicEffect.CloseBottomSheet -> closeBottomSheet()
                CreateBoxPackyMusicEffect.SaveMusic -> {
                    closeBottomSheet()
                    navController.popBackStack(
                        route = CreateBoxBottomSheetRoute.CREATE_BOX_CHOOSE_MUSIC,
                        inclusive = true
                    )
                }

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
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) { index ->
                if (pagerState.currentPage != index) {
                    CreateBoxPackyMusicIntent.ChangeMusicState(
                        index,
                        YoutubeState.PAUSED
                    )
                }
                uiState.music.getOrNull(index)?.let { packMusic ->
                    packMusic.videoId?.let { videoId ->
                        YoutubePlayer(
                            videoId = videoId,
                            packMusic = packMusic,
                            stateChange = viewModel::emitIntent,
                            index = index
                        )
                    }
                }

            }
            Spacer(1f)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                style = buttonStyle.large.black, text = Strings.SAVE
            ) {
                viewModel.emitIntent(CreateBoxPackyMusicIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun YoutubePlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    packMusic: PackyMusic,
    stateChange: emitMviIntent<CreateBoxPackyMusicIntent>,
    index: Int
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        YoutubePlayer(
            videoId = videoId,
            youtubeState = packMusic.state,
            stateListener = { state ->
                stateChange(
                    CreateBoxPackyMusicIntent.ChangeMusicState(
                        index,
                        state
                    )
                )
            },
        )
    }
}
