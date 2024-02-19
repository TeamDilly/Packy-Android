package com.example.home.archive.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.core.widget.giftbox.PhotoForm
import com.packy.domain.model.archive.ArchivePhoto

@Composable
fun ArchivePhotos(
    modifier: Modifier,
    photos: LazyPagingItems<ArchivePhoto>,
    onClick: (ArchivePhoto) -> Unit = {}
) {
    FlagChangeAnimation(
        flag = photos.itemCount == 0 && photos.loadState.refresh !is LoadState.Loading,
        flagOnContent = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.ARCHIVE_TAP_PHOTO_EMPTY,
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
            items(photos.itemCount) { index ->
                val photo = photos[index] ?: return@items
                PhotoForm(
                    modifier = Modifier.clickableWithoutRipple {
                        onClick(photo)
                    },
                    photo = photo.photoUrl
                )
            }
        }
    }
}
