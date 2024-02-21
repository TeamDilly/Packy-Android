package com.example.giftbox.giftarr

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.common.BoxOpenLottie
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.GIFT_BOX_ARR_TITLE
import com.packy.core.widget.animation.BoxShakeAnimation
import com.packy.feature.core.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun GiftBoxArrScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeGiftBox: () -> Unit,
    viewModel: GiftBoxArrViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var lottiePlaying by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            BoxOpenLottie.entries[uiState.giftBox?.box?.id?.toInt()?.minus(1) ?: 0].openLottie
        )
    )
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = lottiePlaying
    )
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    LaunchedEffect(progress) {
        if (progress == 1f) {
            val giftBox = uiState.giftBox
            if (giftBox != null) {
                navController.navigate(GiftBoxRoute.getGiftBoxDetailOpenFadeRoute(giftBox, false))
            } else {
                // TODO ERROR 페이지
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxArrEffect.MoveToBack -> {
                    closeGiftBox()
                }

                GiftBoxArrEffect.MoveToOpenBox -> {
                    lottiePlaying = true
                }
            }
        }
    }

    BackHandler {
        closeGiftBox()
    }

    Scaffold(
        topBar = {
            if (!lottiePlaying) {
                PackyTopBar.Builder()
                    .startIconButton(icon = R.drawable.cancle) {
                        viewModel.emitIntentThrottle(GiftBoxArrIntent.OnBackClick)
                    }
                    .build()
            } else {
                PackyTopBar.Builder().build()
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(height = 48.dp)
                AnimatedVisibility(
                    visible = !lottiePlaying,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = fadeOut(animationSpec = tween(800))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = GIFT_BOX_ARR_TITLE(uiState.giftBox?.senderName ?: ""),
                            style = PackyTheme.typography.heading01.copy(
                                textAlign = TextAlign.Center
                            ),
                            color = PackyTheme.color.gray900,
                        )
                        Spacer(height = 20.dp)
                        Text(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    PackyTheme.color.gray300,
                                    RoundedCornerShape(40.dp)
                                )
                                .background(
                                    PackyTheme.color.gray100,
                                    RoundedCornerShape(40.dp)
                                )
                                .padding(
                                    horizontal = 24.dp,
                                    vertical = 12.dp
                                ),
                            text = uiState.giftBox?.name ?: "",
                            style = PackyTheme.typography.body04.copy(
                                textAlign = TextAlign.Center
                            ),
                            color = PackyTheme.color.gray900,
                        )
                    }
                }
                Spacer(height = 64.dp)
                Box(
                    modifier = Modifier
                        .height(screenHeight * 0.36f)
                        .fillMaxWidth()
                )
                Spacer(1f)
                AnimatedVisibility(
                    visible = !lottiePlaying,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = slideOutVertically(
                        animationSpec = tween(800),
                        targetOffsetY = { it },
                    ) + fadeOut(animationSpec = tween(800))
                ) {
                    Column {
                        PackyButton(
                            modifier = Modifier.padding(horizontal = 24.dp),
                            text = Strings.OPEN,
                            style = buttonStyle.large.black,
                            onClick = {
                                if(!lottiePlaying) {
                                    viewModel.emitIntentThrottle(GiftBoxArrIntent.OnOpenClick)
                                }
                            }
                        )
                        Spacer(height = 16.dp)
                    }
                }
            }
            BoxShakeAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .clickableWithoutRipple {
                        if(!lottiePlaying) {
                            viewModel.emitIntentThrottle(GiftBoxArrIntent.OnOpenClick)
                        }
                    },
                animationPlay = !lottiePlaying
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(16f / 35f)
                )
            }
        }
    }
}