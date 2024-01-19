package com.packy.createbox.createboax.addphoto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.values.Strings
import com.packy.createbox.createboax.addyourmusic.CreateBoxYourMusicIntent
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.feature.core.R

@Composable
fun CreateBoxAddPhotoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxAddPhotoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxAddPhotoEffect.CloseBottomSheet -> closeBottomSheet()
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
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxAddPhotoIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        BottomSheetTitle(
            BottomSheetTitleContent(
                title = Strings.CREATE_BOX_ADD_PHOTO_TITLE,
                description = Strings.CREATE_BOX_ADD_PHOTO_DESCRIPTION,
            )
        )
        Spacer(1f)
        PackyButton(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            style = buttonStyle.large.black,
            text = Strings.SAVE,
            enabled = viewModel.currentState.isSavable
        ) {
            viewModel.emitIntent(CreateBoxAddPhotoIntent.OnSaveClick)
        }
        Spacer(height = 16.dp)
    }

}