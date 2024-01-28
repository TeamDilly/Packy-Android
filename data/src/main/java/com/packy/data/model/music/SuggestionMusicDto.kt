package com.packy.data.model.music

import com.packy.data.model.youtube.YoutubeInfoDto
import com.packy.domain.model.music.Music
import com.packy.lib.ext.extractYouTubeVideoId
import com.packy.lib.ext.validationYoutubeVideoId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionMusicDto(
    @SerialName("title") val title: String,
    @SerialName("hashtags") val hashtags: List<String>,
    @SerialName("id") val id: Int,
    @SerialName("youtubeUrl") val youtubeUrl: String,
    @SerialName("sequence") val sequence: Int
)

fun SuggestionMusicDto.toEntity(): Music = Music(
    id = this.id,
    title = this.title,
    hashtags = this.hashtags,
    youtubeUri = youtubeUrl,
    videoId = extractYouTubeVideoId(youtubeUrl),
)

fun List<SuggestionMusicDto>.toEntity(): List<Music> =
    this.map { music ->
        music.toEntity()
    }