package com.packy.createbox.createboax.addphoto

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.packy.core.permissions.checkAndRequestPermissions
import com.packy.core.permissions.storagePermissions
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.createboax.addgift.CreateBoxAddGiftIntent
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@Composable
fun CreateBoxAddPhotoScreen(
    modifier: Modifier = Modifier,
    closeBottomSheet: (Boolean) -> Unit,
    savePhoto: (Uri, String) -> Unit,
    viewModel: CreateBoxAddPhotoViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.initPhotoItem()
        viewModel.effect.collect { effect ->
            when (effect) {
                is CreateBoxAddPhotoEffect.CloseBottomSheet -> closeBottomSheet(effect.changed)
                is CreateBoxAddPhotoEffect.SavePhotoItem -> {
                    savePhoto(
                        /**
                         * SavePhotoItem을 호출할떄는 [CreateBoxAddPhotoState]
                         * 의 `isSavable`을 확인하고 호출해야합니다.
                         * */
                        effect.photoItem.imageUri!!,
                        effect.photoItem.contentDescription!!
                    )
                }
            }
        }
    }

    BackHandler(true) {
        viewModel.emitIntent(CreateBoxAddPhotoIntent.OnCloseClick)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
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
                imageItem = uiState.photoItem,
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
    imageItem: PhotoItem,
    closeBottomSheet: emitMviIntent<CreateBoxAddPhotoIntent>,
    getPhotoUri: emitMviIntent<CreateBoxAddPhotoIntent>,
    changeDescription: emitMviIntent<CreateBoxAddPhotoIntent>,
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                getPhotoUri(CreateBoxAddPhotoIntent.ChangeImageUri(imageUri = uri))
            }
        }
    val context = LocalContext.current

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            launcher.launch(
                PickVisualMediaRequest(
                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                )
            )
        } else {
            // FIXME 권한 안줬을떄 처리
            println("권한 안줬을때 처리")
        }
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
            .padding(all = 14.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .background(color = PackyTheme.color.white)
                    .fillMaxWidth()
                    .aspectRatio(1f / 1f),
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
            Spacer(height = 16.dp)
            PackyTextField(
                modifier = Modifier,
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