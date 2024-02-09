package com.packy.core.widget.giftbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun TopBoxPartImage(
    boxPartImageUrl: String?,
    modifier: Modifier = Modifier,
) {

    var animation by remember { mutableStateOf(false) }

    LaunchedEffect(boxPartImageUrl) {
        animation = true
    }

    val transition = updateTransition(
        animation,
        label = "boxTransition"
    )

    val offsetX by transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 800
                280.dp at 0
                0.dp at 800
            }
        },
        label = "SlideInOffsetX"
    ) { offsetX ->
        if (offsetX) 0.dp else 280.dp
    }
    GlideImage(
        modifier = modifier
            .width(280.dp)
            .height(160.dp)
            .offset(x = offsetX),
        model = boxPartImageUrl,
        contentDescription = "box guide screen",
        contentScale = ContentScale.Crop
    )
}
