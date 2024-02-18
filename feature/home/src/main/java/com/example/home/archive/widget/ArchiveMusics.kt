package com.example.home.archive.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArchiveMusics(
    modifier: Modifier,
    musics: LazyPagingItems<ArchiveMusic>,
    onClick: (ArchiveMusic) -> Unit = {}
) {
    FlagChangeAnimation(
        flag = musics.itemCount == 0,
        flagOnContent = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.ARCHIVE_TAP_MUSIC_EMPTY,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray600
                )
            }
        }) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            items(musics.itemCount) { index ->
                val music = musics[index] ?: return@items

                ArchiveMusicItem(
                    onClick = onClick,
                    music = music
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArchiveMusicItem(
    onClick: (ArchiveMusic) -> Unit,
    music: ArchiveMusic,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier
        .clickableWithoutRipple {
            onClick(music)
        }
        .clip(CircleShape)
        .aspectRatio(1f)
    ) {
        GlideImage(
            modifier = Modifier
                .fillMaxSize()
                .background(PackyTheme.color.white),
            model = music.thumbnailUrl,
            contentDescription = "thumbnail",
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(
                    color = PackyTheme.color.gray900,
                    shape = CircleShape
                )
                .size(44.dp)
        )
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(
                    color = PackyTheme.color.white,
                    shape = CircleShape
                )
                .size(10.dp)
        )
    }
}

@Composable
@Preview
fun ArchiveMusicsPreview() {
    val music = ArchiveMusic(
        thumbnailUrl = "https://www.example.com/thumbnail.jpg",
        youtubeUrl = "https://www.example.com/music.mp3",
    )
    PackyTheme {
        ArchiveMusicItem(
            onClick = {},
            music = music
        )
    }
}