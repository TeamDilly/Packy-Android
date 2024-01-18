package com.packy.createbox.createboax.addpackymusic

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
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
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.feature.core.R
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
        CreateBoxPackyMusicViewModel.dumyMusic.size
    })


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
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = pagerState,
                beyondBoundsPageCount = 2,
                contentPadding = PaddingValues(horizontal = 70.dp)
            ) { index ->
                val pageOffset = (
                        (pagerState.currentPage - index) + pagerState
                            .currentPageOffsetFraction
                        ).absoluteValue
                Box(modifier = Modifier.fillMaxSize()) {
                    YouTubeCdPlayer(
                        modifier = Modifier
                            .size(
                                180.dp * lerp(
                                    start = 0.88f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            )
                            .align(Alignment.Center),
                        videoId = uiState.music[index].videoId,
                        thumbnail = uiState.music[index].thumbnail,
                        youtubeState = uiState.music[index].state,
                        stateListener = { state ->
                            Log.d("LOGEE", "CreateBoxPackyMusicScreen: $state")
                            viewModel.emitIntent(
                                CreateBoxPackyMusicIntent.ChangeMusicState(
                                    index,
                                    state
                                )
                            )
                        },
                        autoPlay = false
                    )
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
