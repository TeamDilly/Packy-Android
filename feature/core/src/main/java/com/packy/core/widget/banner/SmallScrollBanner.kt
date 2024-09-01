package com.packy.core.widget.banner

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.domain.model.banner.ImageBanner
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun SmallScrollBanner(
    bannerUrlList: List<ImageBanner>,
    currentIndex: Int,
    modifier: Modifier = Modifier,
    clickBanner: (String) -> Unit = {},
) {

    val pagerState = rememberPagerState(pageCount = {
        bannerUrlList.size
    })

    LaunchedEffect(key1 = currentIndex) {
        launch {
            pagerState.scrollToPage(currentIndex)
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(4.1f)
            .clip(shape = RoundedCornerShape(20.dp))
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize(),
            state = pagerState,
        ) {
            GlideImage(
                modifier = modifier
                    .fillMaxSize()
                    .clickableWithoutRipple {
                        clickBanner(bannerUrlList[it].url)
                    },
                model = bannerUrlList[it].imageUrl,
                contentDescription = "banner Image"
            )
        }
    }
}
