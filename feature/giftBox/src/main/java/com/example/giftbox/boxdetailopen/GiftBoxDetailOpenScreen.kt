package com.example.giftbox.boxdetailopen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GiftBoxDetailOpenScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GiftBoxDetailOpenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(
        pageCount = {
            2
        },
    )

    var boxPartAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel) {
        boxPartAnimation = true
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxDetailOpenEffect.MoveToBack -> navController.popBackStack()
                GiftBoxDetailOpenEffect.GiftBoxClose -> navController.popBackStack(
                    route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH,
                    inclusive = true
                )

                is GiftBoxDetailOpenEffect.ShowGift -> TODO()
                is GiftBoxDetailOpenEffect.ShowLetter -> TODO()
                is GiftBoxDetailOpenEffect.ShowPhoto -> TODO()
            }
        }
    }

    Scaffold { innerPadding ->
        VerticalPager(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900),
            state = pagerState,
            userScrollEnabled = uiState.hasGift
        ) {
            when (it) {
                0 -> GiftBoxColumn(
                    modifier = Modifier.fillMaxSize(),
                    boxPartAnimation = boxPartAnimation,
                    uiState = uiState,
                    viewModel = viewModel
                )

                1 -> {
                    GiftDetail(
                        giftImageUrl = uiState.giftBox?.gift?.url,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GiftDetail(
    giftImageUrl: String?,
    modifier: Modifier = Modifier,
    onDownClick: () -> Unit = {}
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
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun GiftBoxColumn(
    modifier: Modifier,
    boxPartAnimation: Boolean,
    uiState: GiftBoxDetailOpenState,
    viewModel: GiftBoxDetailOpenViewModel
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopBoxPartImage(
            modifier = Modifier.align(Alignment.TopEnd),
            boxPartAnimation = boxPartAnimation,
            boxPartImageUrl = uiState.giftBox?.box?.boxPart
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(height = 8.dp)
            GiftBoxTopBar(
                title = "${Strings.BOX_ADD_INFO_SENDER} ${uiState.giftBox?.senderName}",
                onBackClick = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnBackClick) },
                rightButton = {
                    PackyCloseIconButton(style = closeIconButtonStyle.large.white) {
                        viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnCloseClick)
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
                            .weight(42f),
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
            if(uiState.hasGift){
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