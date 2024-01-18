package com.packy.core.widget.youtube

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun YouTubeCdPlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    thumbnail: String
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
    ) {
        YoutubePlayer(
            modifier = modifier
                .aspectRatio(1f)
                .clip(CircleShape),
            videoId = videoId,
        )
        GlideImage(
            modifier = modifier
                .fillMaxSize(),
            model = thumbnail,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}