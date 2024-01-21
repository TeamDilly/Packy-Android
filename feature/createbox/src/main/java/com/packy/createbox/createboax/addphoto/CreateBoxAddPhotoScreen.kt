package com.packy.createbox.createboax.addphoto

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateBoxAddPhotoScreen(
    modifier: Modifier = Modifier,
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
        verticalArrangement = Arrangement.Top,
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
        Spacer(height = 32.dp)
        Box(modifier = Modifier.padding(horizontal = 40.dp)) {
            PhotoFrame(
                imageItem = uiState.imageItem,
                closeBottomSheet = viewModel::emitIntent,
                getPhotoUri = viewModel::emitIntent,
                changeDescription = viewModel::emitIntent,
            )
        }
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PhotoFrame(
    modifier: Modifier = Modifier,
    imageItem: ImageItem,
    closeBottomSheet: emitMviIntent<CreateBoxAddPhotoIntent>,
    getPhotoUri: emitMviIntent<CreateBoxAddPhotoIntent>,
    changeDescription: emitMviIntent<CreateBoxAddPhotoIntent>,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            getPhotoUri(CreateBoxAddPhotoIntent.ChangeImageUri(imageUri = uri))
        }
    Box(
        modifier = modifier
            .clickableWithoutRipple {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                    )
                )
            }
            .background(
                color = PackyTheme.color.gray200,
            )
            .padding(all = 16.dp)
            .fillMaxWidth()
            .height(374.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(color = PackyTheme.color.white)
                    .fillMaxWidth()
                    .weight(80f),
            ) {
                if (imageItem.imageUri != null) {
                    GlideImage(
                        modifier = modifier
                            .align(Alignment.Center)
                            .fillMaxSize(),
                        model = imageItem.imageUri,
                        contentDescription = "Photo",
                        contentScale = ContentScale.Crop,
                    )
                    PackyCloseIconButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .alpha(0.6f),
                        style = closeIconButtonStyle.medium.black
                    ) {
                        closeBottomSheet(CreateBoxAddPhotoIntent.OnCancelImageClick)
                    }
                } else {
                    Icon(
                        modifier = modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = null,
                        tint = PackyTheme.color.gray900,
                    )
                }

            }
            Spacer(6f)
            PackyTextField(
                modifier = Modifier
                    .weight(14f),
                value = imageItem.contentDescription ?: "",
                placeholder = Strings.CREATE_BOX_ADD_PHOTO_PLACEHOLDER,
                textAlign = TextAlign.Center,
                textFieldColor = PackyTheme.color.gray200,
                onValueChange = {
                    changeDescription(
                        CreateBoxAddPhotoIntent.ChangeDescription(
                            newDescription = it
                        )
                    )
                },
                maxLines = 1,
            )
        }
    }
}