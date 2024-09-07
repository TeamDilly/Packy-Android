package com.packy.core.widget.banner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.domain.model.banner.ImageBanner
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun SmallScrollBanner(
    bannerUrlList: List<ImageBanner>,
    modifier: Modifier = Modifier,
    clickBanner: (String) -> Unit = {},
) {

    var autoScrollJob: Job? = null

    val pagerState = rememberPagerState(
        initialPage = if (bannerUrlList.size <= 1) bannerUrlList.size else 499,
        pageCount = {
            if (bannerUrlList.size <= 1) bannerUrlList.size else 1000
        }
    )

    LaunchedEffect(key1 = pagerState.settledPage) {
        if (bannerUrlList.size <= 1) return@LaunchedEffect
        autoScrollJob?.cancel()
        autoScrollJob = launch {
            delay(3000L)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = true,
            state = pagerState,
        ) { index ->
            val item = bannerUrlList[index % bannerUrlList.size]

            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clickableWithoutRipple {
                        clickBanner(item.url)
                    },
                model = item.imageUrl,
                contentDescription = "banner Image",
                contentScale = ContentScale.Crop
            )
        }
        if (bannerUrlList.size > 1) {
            Text(
                modifier = Modifier
                    .padding(
                        end = 12.dp,
                        bottom = 12.dp
                    )
                    .background(
                        color = PackyTheme.color.black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                text = "${(pagerState.currentPage % bannerUrlList.size) + 1} / ${bannerUrlList.size}",
                style = PackyTheme.typography.body06,
                color = PackyTheme.color.white
            )
        }
    }
}

