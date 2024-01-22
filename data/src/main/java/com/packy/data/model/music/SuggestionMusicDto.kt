package com.packy.data.model.music

import com.packy.domain.model.music.Music
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuggestionMusicDto(
    @SerialName("hashtags") val hashtags: List<String>,
    @SerialName("id") val id: Int,
    @SerialName("youtubeUrl") val youtubeUrl: String
)

fun SuggestionMusicDto.toEntity(title: String): Music = Music(
    id = this.id,
    title = title,
    hashtags = this.hashtags,
    youtubeUri = youtubeUrl,
)

//fun List<SuggestionMusicDto>.toEntity(title: List<String>): List<Music> =
//    this.mapIndexed { it.toEntity(tite.) }