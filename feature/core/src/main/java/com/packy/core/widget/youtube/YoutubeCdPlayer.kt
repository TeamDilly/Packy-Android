package com.packy.core.widget.youtube

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.unit.dp

@Composable
fun YouTubeCdPlayer(
    modifier: Modifier = Modifier,
    videoId: String
) {
    YoutubePlayer(
        modifier = modifier
            .aspectRatio(1f,)
            .clip(CircleShape),
        videoId = videoId,
    )
}