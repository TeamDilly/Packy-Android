package com.packy.createbox.boxguide.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme
import com.packy.createbox.boxguide.Photo

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun PhotoForm(photo: Photo) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
            model = photo.photoUrl,
            contentDescription = "box guide photo",
            contentScale = ContentScale.Crop
        )
    }
}