package com.example.home.archive.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.domain.model.archive.ArchiveLetter

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArchiveLetters(
    modifier: Modifier,
    letters: LazyPagingItems<ArchiveLetter>,
    onClick: (ArchiveLetter) -> Unit = {}
) {
    FlagChangeAnimation(
        flag = letters.itemCount == 0 && letters.loadState.refresh !is LoadState.Loading,
        flagOnContent = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.ARCHIVE_TAP_LETTER_EMPTY,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray600
                )
            }
        }) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 32.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
            items(letters.itemCount) { index ->
                val letter = letters[index] ?: return@items
                ArchiveLetterItem(
                    onClick = onClick,
                    letter = letter,
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ArchiveLetterItem(
    onClick: (ArchiveLetter) -> Unit,
    letter: ArchiveLetter,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(163.dp)
            .height(168.dp)
            .clickableWithoutRipple {
                onClick(letter)
            }
    ) {
        Text(
            modifier = modifier
                .background(
                    color = PackyTheme.color.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 11.dp
                )
                .width(163.dp)
                .height(100.dp)
                .align(Alignment.TopStart),
            text = letter.letterContent,
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.gray900,
        )
        GlideImage(
            model = letter.envelopeUrl,
            contentDescription = "box guide Letter",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 36.dp)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
@Preview
fun ArchiveLettersPreview() {
    val letter = ArchiveLetter(
        letterContent = "Letter",
        envelopeUrl = "https://packy-bucket.s3.ap-northeast-2.amazonaws.com/admin/design/envelope/envelope_1%402x.png",
        borderColor = "",
        borderOpacity = 0,
    )
    PackyTheme {
        ArchiveLetterItem(
            letter = letter,
            onClick = {}
        )
    }
}