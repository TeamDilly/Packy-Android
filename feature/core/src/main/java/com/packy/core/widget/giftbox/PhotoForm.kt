package com.packy.core.widget.giftbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun PhotoForm(
    photo: String?,
    modifier: Modifier = Modifier,
    inclination: Float = 0f,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .rotate(inclination)
            .background(
                color = PackyTheme.color.white
            )
            .padding(
                horizontal = 8.dp,
            )
            .padding(
                top = 8.dp,
                bottom = 40.dp
            )
    ) {
        GlideImage(
            modifier = Modifier.aspectRatio(1f),
            model = photo,
            contentDescription = "box guide photo",
            contentScale = ContentScale.Crop
        )
    }
}