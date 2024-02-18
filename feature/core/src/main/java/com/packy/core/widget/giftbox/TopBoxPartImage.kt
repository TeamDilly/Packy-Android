package com.packy.core.widget.giftbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
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
import com.packy.core.widget.animation.ValueChangeAnimation
import kotlinx.coroutines.delay

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun TopBoxPartImage(
    boxPartImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier){
        ValueChangeAnimation(
            value = boxPartImageUrl,
            enterAnimation = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(
                    durationMillis = 500,
                    delayMillis = 300
                )
            ),
            exitAnimation = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(durationMillis = 500)
            )
        ) {
            GlideImage(
                modifier = Modifier
                    .width(280.dp)
                    .height(160.dp),
                model = it,
                contentDescription = "box guide screen",
                contentScale = ContentScale.Crop
            )
        }
    }
}
