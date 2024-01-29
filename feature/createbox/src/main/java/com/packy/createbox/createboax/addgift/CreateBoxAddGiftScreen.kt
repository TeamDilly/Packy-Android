package com.packy.createbox.createboax.addgift

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.CREATE_BOX_ADD_GIFT_CLOSE
import com.packy.core.widget.image.StableIcon
import com.packy.createbox.createboax.common.BottomSheetTitle
import com.packy.createbox.createboax.common.BottomSheetTitleContent
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateBoxAddGiftScreen(
    modifier: Modifier = Modifier,
    closeBottomSheet: () -> Unit,
    saveGift: (Uri?) -> Unit,
    viewModel: CreateBoxAddGiftViewModel = hiltViewModel()
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.emitIntent(CreateBoxAddGiftIntent.ChangeImageUri(imageUri = uri))
        }

    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxAddGiftEffect.CloseBottomSheet -> {
                    saveGift(null)
                    closeBottomSheet()
                }

                is CreateBoxAddGiftEffect.SavePhotoItem -> {
                    saveGift(
                        effect.imageUri
                    )
                    closeBottomSheet()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        PackyTopBar.Builder()
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxAddGiftIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)
        BottomSheetTitle(
            BottomSheetTitleContent(
                title = Strings.CREATE_BOX_ADD_GIFT_TITLE,
                description = Strings.CREATE_BOX_ADD_GIFT_DESCRIPTION,
            )
        )
        Spacer(height = 32.dp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(horizontal = 24.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            if (uiState.imageUri == null) {
                EmptyGiftForm(launcher)
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    GlideImage(
                        modifier = modifier
                            .fillMaxSize(),
                        model = uiState.imageUri,
                        contentDescription = "Gift",
                        alignment = Alignment.TopCenter,
                        contentScale = ContentScale.None,
                    )
                    PackyCloseIconButton(
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                                end = 12.dp
                            )
                            .align(Alignment.TopEnd),
                        style = closeIconButtonStyle.medium.black
                    ) {
                        viewModel.emitIntent(CreateBoxAddGiftIntent.OnCancelImageClick)
                    }
                }
            }
        }
        Spacer(1f)
        PackyButton(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            style = buttonStyle.large.black,
            text = Strings.SAVE,
        ) {
            viewModel.emitIntentThrottle(CreateBoxAddGiftIntent.OnSaveClick)
        }
        Spacer(height = 8.dp)
        Text(
            text = CREATE_BOX_ADD_GIFT_CLOSE,
            style = PackyTheme.typography.body02.copy(
                textAlign = TextAlign.Center,
            ),
            color = PackyTheme.color.gray900,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 14.dp
                )
                .clickableWithoutRipple {
                    viewModel.emitIntentThrottle(CreateBoxAddGiftIntent.OnCloseClick)
                }
        )
        Spacer(height = 20.dp)
    }
}

@Composable
private fun EmptyGiftForm(launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutRipple {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly,
                    )
                )
            },
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = PackyTheme.color.gray200,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            StableIcon(
                modifier = Modifier
                    .size(24.dp),
                imageRes = R.drawable.photo,
                tint = PackyTheme.color.gray900,
                contentDescription = "photo"
            )
        }
    }
}