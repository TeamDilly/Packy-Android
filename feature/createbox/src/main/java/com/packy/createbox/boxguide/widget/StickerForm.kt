package com.packy.createbox.boxguide.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StickerForm(
    modifier: Modifier = Modifier,
    stickerUri: String? = null,
    inclination: Float = 0f,
) {
    Box(modifier = modifier) {
        if (stickerUri != null) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(inclination),
                model = stickerUri,
                contentDescription = "sticker image",
                contentScale = ContentScale.Crop,
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.plus_square_dashed),
                contentDescription = "sticker placeholder icon",
                tint = PackyTheme.color.white,
            )
        }
    }
}
