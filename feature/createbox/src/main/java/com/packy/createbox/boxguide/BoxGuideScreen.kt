package com.packy.createbox.boxguide

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
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
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.COMPLETE
import com.packy.core.widget.giftbox.GiftBoxTopBar
import com.packy.core.widget.giftbox.LetterForm
import com.packy.core.widget.giftbox.MusicForm
import com.packy.core.widget.giftbox.PhotoForm
import com.packy.core.widget.giftbox.TopBoxPartImage
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.createbox.boxguide.widget.BoxGuideBottomSheet
import com.packy.createbox.boxguide.widget.BoxGuideContent
import com.packy.createbox.boxguide.widget.BoxPlaceholder
import com.packy.core.widget.giftbox.StickerForm
import com.packy.createbox.createboax.addgift.CreateBoxAddGiftScreen
import com.packy.feature.core.R
import com.packy.createbox.createboax.addlatter.CreateBoxLetterScreen
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoScreen
import com.packy.createbox.createboax.addsticker.CreateBoxStickerScreen
import com.packy.createbox.createboax.boxchange.CreateBoxChangeScreen
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.domain.model.box.BoxDesign
import com.packy.domain.model.createbox.Sticker
import com.packy.lib.ext.extractYouTubeVideoId
import com.packy.lib.ext.removeNewlines
import com.packy.mvi.ext.emitMviIntent

@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxGuideViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }

    var bottomSheetRoute by remember { mutableStateOf(BoxGuideBottomSheetRoute.EMPTY) }

    var boxPartAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.initUiState()
        boxPartAnimation = true

        viewModel.effect.collect { effect ->
            when (effect) {
                is BoxGuideEffect.MoveToBack -> navController.popBackStack()
                is BoxGuideEffect.SaveBox -> navController.navigate(CreateBoxRoute.BOX_ADD_TITLE)
                is BoxGuideEffect.ShowBottomSheet -> {
                    bottomSheetRoute = effect.boxGuideBottomSheetRoute
                    showBottomSheet = true
                }

                is BoxGuideEffect.FailedSaveBox -> TODO()
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900)
        ) {
            TopBoxPartImage(
                modifier = Modifier.align(Alignment.TopEnd),
                boxPartAnimation = boxPartAnimation,
                boxPartImageUrl = uiState.boxDesign?.boxPart,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(height = 8.dp)
                GiftBoxTopBar(
                    title = uiState.title,
                    onBackClick = {
                        viewModel.emitIntentThrottle(BoxGuideIntent.OnBackClick)
                    },
                    rightButton = {
                        RightMenuTopbar(
                            isBoxComplete = uiState.isBoxComplete(),
                            onClick = {
                                viewModel.emitIntentThrottle(BoxGuideIntent.OnSaveClick)
                            }
                        )
                    }
                )
                Spacer(height = 31.dp)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BoxGuideContent(
                            modifier = Modifier
                                .aspectRatio(160f / 192f)
                                .fillMaxWidth()
                                .weight(38f),
                            inclination = -3f,
                            placeholder = {
                                BoxPlaceholder(
                                    icon = R.drawable.photo,
                                    title = Strings.BOX_GUIDE_PHOTO
                                )
                            },
                            content = uiState.photo?.let { photo ->
                                {
                                    PhotoForm(photo = photo.photoUrl.toString())
                                }
                            },
                            onClick = {
                                viewModel.emitIntentThrottle(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_PHOTO))
                            }
                        )
                        Spacer(28.dp)
                        StickerForm(
                            modifier = Modifier
                                .aspectRatio(1f / 1f)
                                .weight(28f),
                            inclination = 10f,
                            stickerUri = uiState.selectedSticker.sticker1?.imgUrl,
                            onClick = { viewModel.emitIntentThrottle(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_STICKER_1)) }
                        )
                    }
                    Spacer(height = 20.dp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        StickerForm(
                            modifier = Modifier
                                .aspectRatio(1f / 1f)
                                .weight(30f),
                            inclination = -10f,
                            stickerUri = uiState.selectedSticker.sticker2?.imgUrl,
                            onClick = { viewModel.emitIntentThrottle(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_STICKER_2)) }
                        )
                        Spacer(22.dp)
                        BoxGuideContent(
                            modifier = Modifier
                                .aspectRatio(180f / 150f)
                                .fillMaxWidth()
                                .weight(42f),
                            inclination = 3f,
                            placeholder = {
                                BoxPlaceholder(
                                    icon = R.drawable.envelope,
                                    title = Strings.BOX_GUIDE_Letter
                                )
                            },
                            content = uiState.letter?.let { letter ->
                                {
                                    LetterForm(
                                        letterContent = letter.letterContent,
                                        envelopeUrl = letter.envelope.envelopeUrl
                                    )
                                }
                            },
                            onClick = {
                                viewModel.emitIntentThrottle(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_LATTER))
                            }
                        )
                    }
                    Spacer(height = 29.dp)
                    BoxGuideContent(
                        modifier = Modifier
                            .heightIn(
                                min = 0.dp,
                                max = 146.dp
                            )
                            .aspectRatio(16f / 9f)
                            .fillMaxWidth(),
                        inclination = 0f,
                        placeholder = {
                            BoxPlaceholder(
                                icon = R.drawable.music_note,
                                title = Strings.BOX_GUIDE_MUSIC
                            )
                        },
                        content = uiState.youtubeUrl?.let { youtubeUri ->
                            {
                                MusicForm(
                                    youtubeUri = youtubeUri,
                                    youtubeState = uiState.youtubeState,
                                    clearMusic = {
                                        viewModel.emitIntentThrottle(BoxGuideIntent.ClearMusic)
                                    }
                                )
                            }
                        },
                        onClick = {
                            viewModel.emitIntentThrottle(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_MUSIC))
                        }
                    )
                    Spacer(1f)
                    BottomNavButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        showBottomSheet = viewModel::emitIntentThrottle
                    )
                    Spacer(height = 28.dp)
                }
            }
            BoxGuideBottomSheet(
                visible = showBottomSheet,
                changeVisible = {
                    showBottomSheet = false
                }
            ) {
                BottomSheetNav(
                    uiState = uiState,
                    bottomSheetRoute = bottomSheetRoute,
                    closeBottomSheet = {
                        showBottomSheet = false
                    },
                    savePhoto = { uri, description ->
                        viewModel.emitIntent(
                            BoxGuideIntent.SavePhoto(
                                uri,
                                description
                            )
                        )
                    },
                    saveLetter = { envelopeId, envelopeUri, letterText ->
                        viewModel.emitIntent(
                            BoxGuideIntent.SaveLetter(
                                Letter(
                                    letterContent = letterText,
                                    envelope = Envelope(
                                        envelopeId,
                                        envelopeUri,
                                    )
                                )
                            )
                        )
                    },
                    saveMusic = { youtubeUrl ->
                        viewModel.emitIntent(
                            BoxGuideIntent.SaveMusic(
                                youtubeUrl
                            )
                        )
                    },
                    onSaveSticker = { index, sticker ->
                        viewModel.emitIntent(
                            BoxGuideIntent.SaveSticker(
                                index,
                                sticker
                            )
                        )
                    },
                    saveGift = { uri ->
                        viewModel.emitIntent(
                            BoxGuideIntent.SaveGift(
                                uri,
                            )
                        )
                    },
                    onSaveBox = {
                        viewModel.emitIntent(
                            BoxGuideIntent.SaveBox(
                                it
                            )
                        )
                    },
                )
            }
        }
    }
}

@Composable
private fun BottomNavButton(
    modifier: Modifier = Modifier,
    showBottomSheet: emitMviIntent<BoxGuideIntent>,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp),
    ) {
        BottomButton(
            modifier = Modifier
                .height(50.dp)
                .weight(1f),
            onClick = {
                showBottomSheet(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.CHANGE_BOX))
            },
        ) {
            Text(
                text = Strings.BOX_GUIDE_CHANGE_BOX,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.white
            )
        }
        Spacer(width = 16.dp)
        BottomButton(
            modifier = Modifier
                .height(50.dp)
                .weight(1f),
            onClick = {
                showBottomSheet(BoxGuideIntent.ShowBottomSheet(BoxGuideBottomSheetRoute.ADD_GIFT))
            },
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "box guide add gift button",
                    tint = PackyTheme.color.white,
                )
                Spacer(width = 8.dp)
                Text(
                    text = Strings.BOX_GUIDE_GIFT,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.white
                )
            }
        }
    }
}

@Composable
private fun BottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = PackyTheme.color.black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(100.dp)
            )
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun RightMenuTopbar(
    isBoxComplete: Boolean,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .width(60.dp)
        .height(40.dp)
        .background(
            color = PackyTheme.color.white,
            shape = RoundedCornerShape(80.dp)
        )
        .clickableWithoutRipple(
            enabled = isBoxComplete,
        ) {
            onClick()
        }
    )
    {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = COMPLETE,
            style = PackyTheme.typography.body02.copy(
                textAlign = TextAlign.Center
            ),
            color = if (isBoxComplete)
                PackyTheme.color.gray900
            else PackyTheme.color.gray600
        )
    }
}

@Composable
private fun BottomSheetNav(
    uiState: BoxGuideState,
    modifier: Modifier = Modifier,
    bottomSheetRoute: BoxGuideBottomSheetRoute,
    closeBottomSheet: () -> Unit,
    savePhoto: (Uri, String) -> Unit,
    saveLetter: (Int, String, String) -> Unit,
    saveMusic: (String) -> Unit,
    onSaveSticker: (Int, Sticker?) -> Unit,
    saveGift: (Uri?) -> Unit,
    onSaveBox: (BoxDesign) -> Unit,
) {
    when (bottomSheetRoute) {
        BoxGuideBottomSheetRoute.ADD_GIFT -> {
            CreateBoxAddGiftScreen(
                closeBottomSheet = closeBottomSheet,
                saveGift = saveGift
            )
        }

        BoxGuideBottomSheetRoute.ADD_LATTER -> {
            CreateBoxLetterScreen(
                closeBottomSheet = closeBottomSheet,
                saveLetter = saveLetter
            )
        }

        BoxGuideBottomSheetRoute.ADD_MUSIC -> {
            CreateBoxNavHost(
                modifier = Modifier.background(PackyTheme.color.white),
                closeBottomSheet = closeBottomSheet,
                saveMusic = saveMusic
            )
        }

        BoxGuideBottomSheetRoute.ADD_PHOTO ->
            CreateBoxAddPhotoScreen(
                closeBottomSheet = closeBottomSheet,
                savePhoto = savePhoto
            )

        BoxGuideBottomSheetRoute.ADD_STICKER_1 -> CreateBoxStickerScreen(
            stickerIndex = 1,
            selectedSticker = uiState.selectedSticker,
            closeBottomSheet = closeBottomSheet,
            onSaveSticker = {
                onSaveSticker(
                    1,
                    it
                )
            },
        )

        BoxGuideBottomSheetRoute.ADD_STICKER_2 -> CreateBoxStickerScreen(
            stickerIndex = 2,
            selectedSticker = uiState.selectedSticker,
            closeBottomSheet = closeBottomSheet,
            onSaveSticker = {
                onSaveSticker(
                    2,
                    it
                )
            },
        )

        BoxGuideBottomSheetRoute.CHANGE_BOX -> CreateBoxChangeScreen(
            currentBox = uiState.boxDesign!!,
            closeBottomSheet = closeBottomSheet,
            onSaveBox = onSaveBox,
        )

        BoxGuideBottomSheetRoute.EMPTY -> {
            closeBottomSheet()
        }
    }
}