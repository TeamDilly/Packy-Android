package com.packy.core.widget.youtube

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.animations.RotationAnimation
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun YouTubeCdPlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    thumbnail: String,
    youtubeState: YoutubeState,
    stateListener: (YoutubeState) -> Unit = {},
    autoPlay: Boolean = true,
) {

    val isPlaying = youtubeState == YoutubeState.PLAYING

    Box(
        modifier = modifier
            .clip(CircleShape)
    ) {
        RotationAnimation(
            modifier = Modifier
                .fillMaxSize(),
            isPlaying = isPlaying,
            durationMillis = 8000,
            pauseDurationMillis = 2500
        ) {
            YoutubePlayer(
                modifier = modifier,
                videoId = videoId,
                stateListener = stateListener,
                youtubeState = youtubeState,
                autoPlay = autoPlay
            )
            GlideImage(
                modifier = modifier
                    .fillMaxSize(),
                model = thumbnail,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Box(modifier = Modifier
            .clickableWithoutRipple {
                stateListener(
                    if (isPlaying) YoutubeState.PAUSED else YoutubeState.PLAYING
                )
            }
            .size(44.dp)
            .background(
                color = PackyTheme.color.white,
                shape = CircleShape
            )
            .align(Alignment.Center))
        {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center),
                painter = painterResource(
                    id = if (isPlaying) R.drawable.play_fill
                    else R.drawable.pause
                ),
                contentDescription = "YoutubeCd PlayIcon",
                tint = PackyTheme.color.gray900
            )
        }
    }
}