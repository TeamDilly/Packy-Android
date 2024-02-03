package com.packy.core.widget.giftbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun TopBoxPartImage(
    boxPartAnimation: Boolean,
    boxPartImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = boxPartAnimation,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 800)
        )
    ) {
        GlideImage(
            modifier = Modifier
                .width(280.dp)
                .height(160.dp),
            model = boxPartImageUrl,
            contentDescription = "box guide screen",
            contentScale = ContentScale.Crop
        )
    }
}
