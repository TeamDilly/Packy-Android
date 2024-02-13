package com.packy.createbox.boxmotion

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.BoxOpenLottie
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.TextAnimation
import com.packy.createbox.navigation.CreateBoxRoute
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun BoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    boxId: Int,
) {

    val (textAlpha, textOffsetY) = TextAnimation()

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val lottieAnimation = BoxOpenLottie.entries.getOrNull(boxId - 1) ?: BoxOpenLottie.BOX_OPEN_1
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieAnimation.lottie))
        val progress by animateLottieCompositionAsState(
            composition,
            isPlaying = true
        )

        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(16f / 35f)
                .align(Alignment.Center),
            composition = composition,
            progress = { progress },
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 104.dp)
                .offset(y = textOffsetY)
                .alpha(textAlpha),
            text = Strings.CREATE_BOX_MOTION_TITLE,
            style = PackyTheme.typography.heading01.copy(
                textAlign = TextAlign.Center
            ),
            color = PackyTheme.color.gray900,
        )

        if (progress == 1f) {
            navController.navigate(CreateBoxRoute.BOX_GUIDE_FADE_IN) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                currentRoute?.let { popUpTo(it) { inclusive = true } }
                launchSingleTop = true
            }
        }
    }
}