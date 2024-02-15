package com.packy.core.widget.giftbox

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.lib.ext.extractYouTubeVideoId

@Composable
fun MusicForm(
    modifier: Modifier = Modifier,
    youtubeUri: String,
    youtubeState: YoutubeState,
    autoPlay: Boolean = true,
    clearMusic: (() -> Unit)? = null
) {
    val youtubeVideoId = extractYouTubeVideoId(youtubeUri)
    if (youtubeVideoId != null) {
        Box(modifier = modifier.fillMaxSize()) {
            YoutubePlayer(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 9f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp)),
                videoId = youtubeVideoId,
                autoPlay = autoPlay,
                youtubeState = youtubeState,
            )
            clearMusic?.let {
                PackyCloseIconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    style = closeIconButtonStyle.medium.white
                ) {
                    clearMusic()
                }
            }
        }
    }
}