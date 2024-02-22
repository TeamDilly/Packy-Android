package com.example.giftbox.boxdetailopen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.common.authenticator.ext.colorCodeToColor
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.giftbox.GiftBoxTopBar
import com.packy.core.widget.giftbox.LetterForm
import com.packy.core.widget.giftbox.MusicForm
import com.packy.core.widget.giftbox.PhotoForm
import com.packy.core.widget.giftbox.StickerForm
import com.packy.core.widget.giftbox.TopBoxPartImage
import com.packy.feature.core.R
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun GiftBoxDetailOpenScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    showBackArrow: Boolean,
    closeGiftBox: () -> Unit,
    moveToShared: (Long) -> Unit,
    viewModel: GiftBoxDetailOpenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()
    val showDialog by remember {
        derivedStateOf { uiState.showDetail }
    }
    val pagerState = rememberPagerState(
        pageCount = {
            2
        },
    )

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxDetailOpenEffect.MoveToBack -> {
                    navController.popBackStack()
                }

                GiftBoxDetailOpenEffect.GiftBoxClose -> {
                    closeGiftBox()
                }
            }
        }
    }

    BackHandler(true) {
        when {
            showDialog != ShowDetail.NONE -> viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.CloseDialog)
            pagerState.currentPage != 0 -> scope.launch {
                pagerState.animateScrollToPage(0)
            }

            showBackArrow -> navController.popBackStack()
            else -> closeGiftBox()
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900),
        ) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.TopEnd),
                visible = pagerState.currentPage == 0,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(
                        durationMillis = 400,
                        delayMillis = 200
                    )
                )
            ) {
                TopBoxPartImage(
                    boxPartImageUrl = uiState.giftBox?.box?.boxTop
                )
            }

            VerticalPager(
                modifier = Modifier
                    .blur(if (showDialog != ShowDetail.NONE) 12.dp else 0.dp),
                state = pagerState,
                userScrollEnabled = uiState.hasGift,
                beyondBoundsPageCount = 2
            ) {
                when (it) {
                    0 -> GiftBoxColumn(
                        modifier = Modifier.fillMaxSize(),
                        uiState = uiState,
                        viewModel = viewModel,
                        showBackArrow = showBackArrow
                    )

                    1 -> {
                        GiftDetail(
                            giftImageUrl = uiState.giftBox?.gift?.url,
                            modifier = Modifier.fillMaxSize(),
                            onDownClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(0)
                                }
                            },
                            onGiftClick = {
                                viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnGiftClick)
                            }
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = showDialog != ShowDetail.NONE,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 400)
                ) + scaleIn(
                    animationSpec = tween(durationMillis = 400)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 400)
                ) + scaleOut(
                    animationSpec = tween(durationMillis = 400)
                )
            ) {
                when (showDialog) {
                    ShowDetail.PHOTO -> Dialog(
                        modifier = Modifier.padding(horizontal = 38.dp),
                        click = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.CloseDialog) }
                    ) {
                        Column(
                            modifier = Modifier
                                .background(PackyTheme.color.white)
                                .padding(16.dp)
                        ) {
                            GlideImage(
                                modifier = Modifier
                                    .aspectRatio(1f / 1f),
                                model = uiState.giftBox?.photos?.firstOrNull()?.photoUrl,
                                contentDescription = "photo",
                                contentScale = ContentScale.Crop
                            )
                            Spacer(height = 16.dp)
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 12.dp,
                                        horizontal = 40.dp
                                    ),
                                text = uiState.giftBox?.photos?.firstOrNull()?.description ?: "",
                                style = PackyTheme.typography.body04.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = PackyTheme.color.gray900
                            )
                        }
                    }

                    ShowDetail.LETTER -> Dialog(
                        modifier = Modifier
                            .padding(horizontal = 24.dp),
                        click = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.CloseDialog) }
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = PackyTheme.color.white,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .border(
                                    width = 6.dp,
                                    color = uiState.giftBox?.envelope?.borderColorCode.colorCodeToColor(
                                        fallbackColor = PackyTheme.color.gray200,
                                        alpha = uiState.giftBox?.envelope?.opacity
                                            ?.toFloat()
                                            ?.times(0.01f) ?: 1f
                                    ),
                                    shape = RoundedCornerShape(16.dp)

                                )
                                .aspectRatio(1f / 1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(20.dp),
                                text = uiState.giftBox?.letterContent ?: "",
                                style = PackyTheme.typography.body04.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = PackyTheme.color.gray900
                            )
                        }
                    }

                    ShowDetail.GIFT -> Dialog(
                        modifier = Modifier
                            .padding(
                                horizontal = 55.dp,
                                vertical = 140.dp
                            ),
                        click = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.CloseDialog) }
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnGiftClick)
                                },
                            model = uiState.giftBox?.gift?.url,
                            contentDescription = "gift",
                            contentScale = ContentScale.Fit
                        )
                    }

                    ShowDetail.NONE -> Unit
                }
            }
        }
    }
}

@Composable
private fun Dialog(
    click: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickableWithoutRipple {
                click()
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .blur(12.dp)
        )
        content()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GiftDetail(
    giftImageUrl: String?,
    modifier: Modifier = Modifier,
    onDownClick: () -> Unit = {},
    onGiftClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    start = 16.dp
                )
                .size(40.dp)
                .background(
                    color = PackyTheme.color.white,
                    shape = CircleShape
                )
                .clickableWithoutRipple {
                    onDownClick()
                }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "back guide screen"
            )
        }
        Box(
            modifier = Modifier
                .aspectRatio(8f / 9f)
                .align(Alignment.Center)
                .padding(horizontal = 35.dp)
                .clickableWithoutRipple {
                    onGiftClick()
                }
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.gift_frame),
                contentDescription = "gift_frame"
            )
            GlideImage(
                modifier = Modifier
                    .padding(
                        vertical = 40.dp,
                        horizontal = 20.dp
                    )
                    .aspectRatio(1f / 1f)
                    .align(Alignment.Center),
                model = giftImageUrl,
                contentDescription = "gift_image",
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.None,
            )
        }
    }
}

@Composable
private fun GiftBoxColumn(
    modifier: Modifier,
    showBackArrow: Boolean,
    uiState: GiftBoxDetailOpenState,
    viewModel: GiftBoxDetailOpenViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(height = 8.dp)
            GiftBoxTopBar(
                title = "${Strings.BOX_ADD_INFO_SENDER} ${uiState.giftBox?.senderName}",
                showBackArrow = showBackArrow,
                onBackClick = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnBackClick) },
                rightButton = {
                    if (uiState.shouldShowShared) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = PackyTheme.color.white,
                                    shape = CircleShape
                                )
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 11.dp
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = Strings.SEND,
                                style = PackyTheme.typography.body02,
                                color = PackyTheme.color.gray900
                            )
                        }
                    } else {
                        PackyCloseIconButton(style = closeIconButtonStyle.large.white) {
                            viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnCloseClick)
                        }
                    }
                }
            )
            Spacer(1f)
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
                    PhotoForm(
                        modifier = Modifier
                            .aspectRatio(160f / 192f)
                            .fillMaxWidth()
                            .weight(38f)
                            .clickableWithoutRipple {
                                viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnPhotoClick)
                            },
                        inclination = -3f,
                        photo = uiState.giftBox?.photos?.firstOrNull()?.photoUrl
                    )
                    Spacer(28.dp)
                    StickerForm(
                        modifier = Modifier
                            .aspectRatio(1f / 1f)
                            .weight(28f),
                        inclination = 10f,
                        stickerUri = uiState.giftBox?.stickers?.firstOrNull()?.imgUrl,
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
                        stickerUri = uiState.giftBox?.stickers?.getOrNull(1)?.imgUrl,
                    )
                    Spacer(22.dp)
                    LetterForm(
                        modifier = Modifier
                            .aspectRatio(180f / 150f)
                            .fillMaxWidth()
                            .weight(42f)
                            .clickableWithoutRipple {
                                viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnLetterClick)
                            },
                        inclination = 3f,
                        letterContent = uiState.giftBox?.letterContent ?: "",
                        envelopeUrl = uiState.giftBox?.envelope?.imgUrl,
                    )
                }
                Spacer(height = 29.dp)
                MusicForm(
                    modifier = Modifier
                        .heightIn(
                            min = 0.dp,
                            max = 146.dp
                        )
                        .aspectRatio(16f / 9f)
                        .fillMaxWidth(),
                    youtubeUri = uiState.giftBox?.youtubeUrl ?: "",
                    youtubeState = uiState.youtubeState,
                )
            }
            Spacer(1f)
            if (uiState.hasGift) {
                Icon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.arrow_up),
                    contentDescription = "Arrow Icon",
                    tint = PackyTheme.color.white
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = Strings.GIFT_BOX_OPEN_GIFT,
                    style = PackyTheme.typography.body02.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = PackyTheme.color.white
                )
                Spacer(32.dp)
            }
        }
    }
}