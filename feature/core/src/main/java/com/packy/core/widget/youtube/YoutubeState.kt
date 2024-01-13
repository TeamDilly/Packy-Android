package com.packy.core.widget.youtube

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants

enum class YoutubeState {
    UNKNOWN,
    INIT,
    ENDED,
    PLAYING,
    PAUSED,
    BUFFERING;
}


fun PlayerConstants.PlayerState.toState(): YoutubeState = when (this) {
    PlayerConstants.PlayerState.VIDEO_CUED,
    PlayerConstants.PlayerState.UNKNOWN,
    PlayerConstants.PlayerState.UNSTARTED -> YoutubeState.UNKNOWN

    PlayerConstants.PlayerState.ENDED -> YoutubeState.ENDED
    PlayerConstants.PlayerState.PLAYING -> YoutubeState.PLAYING
    PlayerConstants.PlayerState.PAUSED -> YoutubeState.PAUSED
    PlayerConstants.PlayerState.BUFFERING -> YoutubeState.BUFFERING
}