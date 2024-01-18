package com.packy.createbox.createboax.addpackymusic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
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
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = Strings.CREATE_BOX_ADD_PACKY_MUSIC_TITLE,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900
            )
            Spacer(height = 4.dp)
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                text = Strings.CREATE_BOX_ADD_PACKY_MUSIC_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
            Spacer(1f)
        }
    }
}