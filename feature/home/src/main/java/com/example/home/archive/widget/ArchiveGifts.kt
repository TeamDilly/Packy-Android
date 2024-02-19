package com.example.home.archive.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.createbox.box.Gift
import kotlinx.coroutines.processNextEventInCurrentThread

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArchiveGifts(
    modifier: Modifier,
    gifts: LazyPagingItems<ArchiveGift>,
    onClick: (ArchiveGift) -> Unit = {}
) {
    FlagChangeAnimation(
        flag = gifts.itemCount == 0,
        flagOnContent = {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.ARCHIVE_TAP_GIFT_EMPTY,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray600
                )
            }
        }) {
        LazyVerticalStaggeredGrid(
            modifier = modifier.fillMaxSize(),
            columns = StaggeredGridCells.Fixed(2),
            verticalItemSpacing = 16.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
            items(gifts.itemCount) { index ->
                val gift = gifts[index] ?: return@items
                GlideImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickableWithoutRipple {
                            onClick(gift)
                        },
                    model = gift.giftUrl,
                    contentDescription = "gift",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}