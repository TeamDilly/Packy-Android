package com.packy.createbox.boxmotion

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.navigation.CreateBoxRoute
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    boxFull: String?,
    boxBottom: String?
) {
    val openDelay = 500L
    val animationDuration = 1500
    val configuration = LocalConfiguration.current
    var isOpen by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        delay(openDelay)
        isOpen = true
        delay(animationDuration.toLong())
        navController.navigate(CreateBoxRoute.BOX_GUIDE_FADE_IN) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            currentRoute?.let { popUpTo(it) { inclusive = true } }
            launchSingleTop = true
        }
    }
    val transition = updateTransition(
        isOpen,
        label = "boxTransition"
    )

    val boxFullPositionOffsetXDp = (configuration.screenWidthDp / 2)
    val boxFullPositionOffsetX by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = animationDuration
                0.dp at 0 with FastOutSlowInEasing
                boxFullPositionOffsetXDp.dp at animationDuration
            }
        },
        label = "boxFullPositionOffset"
    ) { boxOpen ->
        if (boxOpen) boxFullPositionOffsetXDp.dp else 0.dp
    }
    val boxFullPositionOffsetYDp = (-(configuration.screenWidthDp / 2) - 240)
    val boxFullPositionOffsetY by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = animationDuration
                0.dp at 0 with FastOutSlowInEasing
                boxFullPositionOffsetYDp.dp at animationDuration
            }
        },
        label = "boxFullPositionOffset"
    ) { boxOpen ->
        if (boxOpen) boxFullPositionOffsetYDp.dp else 0.dp
    }
    val boxFullAlpha by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = animationDuration
                1f at 0 with FastOutSlowInEasing
                0f at animationDuration
            }
        },
        label = "boxFullAlpha"
    ) { boxOpen ->
        if (boxOpen) 0f else 1f
    }

    val boxFullRotation by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = animationDuration
                0f at 0 with FastOutSlowInEasing
                25f at animationDuration
            }
        },
        label = "boxFullRotation"
    ) { boxOpen ->
        if (boxOpen) 25f else 0f
    }

    val addedSize = 300.dp
    val boxSize = 240.dp
    val desiredScaleFactor = (configuration.screenHeightDp.dp + addedSize) / boxSize
    val scaleFactor by transition.animateFloat(
        transitionSpec = {
            keyframes {
                durationMillis = animationDuration
                1f at 0 with FastOutSlowInEasing
                desiredScaleFactor at animationDuration
            }
        },
        label = "boxScaleFactor"
    ) { boxOpen ->
        if (boxOpen) desiredScaleFactor else 1f
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = Strings.CREATE_BOX_MOTION_TITLE,
            style = PackyTheme.typography.heading01.copy(
                textAlign = TextAlign.Center
            ),
            color = PackyTheme.color.gray900,
        )
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .scale(scaleFactor)
                    .size(boxSize),
                model = boxBottom,
                contentDescription = "design box bottom",
            )
            GlideImage(
                modifier = Modifier
                    .size(boxSize)
                    .graphicsLayer(
                        rotationZ = boxFullRotation,
                    )
                    .offset(
                        x = boxFullPositionOffsetX,
                        y = boxFullPositionOffsetY
                    )
                    .alpha(boxFullAlpha),
                model = boxFull,
                contentDescription = "design box part"
            )
        }
    }
}