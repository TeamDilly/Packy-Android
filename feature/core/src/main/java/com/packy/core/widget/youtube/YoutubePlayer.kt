package com.packy.core.widget.youtube

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun YoutubePlayer(
    modifier: Modifier = Modifier,
    videoId: String,
    youtubeState: YoutubeState,
    stateListener: (YoutubeState) -> Unit = {},
    autoPlay: Boolean = false,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var player by remember {
        mutableStateOf<YouTubePlayer?>(null)
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
           if (event == Lifecycle.Event.ON_STOP || event == Lifecycle.Event.ON_PAUSE) {
                player?.pause()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            player?.pause()
        }
    }
    if (youtubeState == YoutubeState.PLAYING) {
        player?.play()
    }
    if (youtubeState == YoutubeState.PAUSED) {
        player?.pause()
    }
    AndroidView(
        modifier = modifier,
        factory = {
            val view = YouTubePlayerView(it)
            lifecycleOwner.lifecycle.addObserver(view)
            view.enableAutomaticInitialization = false
            view.isClickable = false
            view.initialize(
                youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        CoroutineScope(Dispatchers.IO).launch {
                            if(autoPlay) {
                                youTubePlayer.loadVideo(
                                    videoId,
                                    0f
                                )
                            } else {
                                youTubePlayer.cueVideo(
                                    videoId,
                                    0f
                                )
                            }
                        }

                        player = youTubePlayer
                        youTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                            override fun onStateChange(
                                youTubePlayer: YouTubePlayer,
                                state: PlayerConstants.PlayerState
                            ) {
                                super.onStateChange(youTubePlayer, state)
                                when (state) {
                                    PlayerConstants.PlayerState.PLAYING -> {
                                        stateListener(YoutubeState.PLAYING)
                                    }
                                    PlayerConstants.PlayerState.PAUSED -> {
                                        stateListener(YoutubeState.PAUSED)
                                    }
                                    else -> Unit
                                }
                            }
                        })
                    }
                },
                playerOptions = IFramePlayerOptions.Builder()
                    .autoplay(if (autoPlay) 1 else 0)
                    .controls(0)
                    .ccLoadPolicy(0)
                    .ivLoadPolicy(3)
                    .build()
            )
            view.apply {
                this.setLayoutParams(
                    android.widget.FrameLayout.LayoutParams(
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                        android.widget.FrameLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }
        })

}