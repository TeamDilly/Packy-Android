package com.example.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.feature.core.R
import com.example.home.navigation.HomeRoute
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.home.HomeBox

@Composable
fun HomeScreen(
    navController: NavController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val giftBoxes = uiState.giftBoxes

    LaunchedEffect(viewModel) {
        viewModel.getGiftBoxse()
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.MoveToSetting -> {
                    navController.navigate(HomeRoute.SETTING)
                }

                is HomeEffect.MoveToBoxDetail -> moveToBoxDetail(effect.boxId)
                HomeEffect.MoveToCreateBox -> moveToCreateBox()
                HomeEffect.MoveToMoreBox -> navController.navigate(HomeRoute.MY_BOX)
            }
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .showLogo()
                .endIconButton(
                    icon = R.drawable.setting
                ) {
                    viewModel.emitIntentThrottle(HomeIntent.OnSettingClick)
                }
                .build()
        },
        containerColor = PackyTheme.color.gray100
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            Spacer(height = 16.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(358.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = PackyTheme.color.gray900,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickableWithoutRipple {
                        viewModel.emitIntentThrottle(HomeIntent.OnCrateBoxClick)
                    }
            )
            Spacer(height = 16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = PackyTheme.color.white,
                        shape = RoundedCornerShape(24.dp)
                    ),
            ) {
                Spacer(height = 24.dp)
                HomeGiftBoxes(
                    giftBoxes = giftBoxes,
                    onMoreClick = { viewModel.emitIntentThrottle(HomeIntent.OnMoreBoxClick) }
                )
                Spacer(height = 24.dp)
            }
        }
    }
}

@Composable
private fun HomeGiftBoxes(
    giftBoxes: List<HomeBox>,
    onMoreClick: () -> Unit = {}
) {
    GiftBoxesTitle(
        modifier = Modifier
            .padding(24.dp),
        onMoreClick = onMoreClick
    )
    LazyRow(
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 16.dp),

        ) {
        item { Spacer(width = 8.dp) }
        items(giftBoxes) { homeBox ->
            GiftBoxItem(
                modifier = Modifier,
                homeBox = homeBox
            )
        }
        item { Spacer(width = 8.dp) }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GiftBoxItem(
    modifier: Modifier = Modifier,
    homeBox: HomeBox
) {
    Column(
        modifier = modifier
    ) {
        GlideImage(
            modifier = Modifier
                .height(138.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = homeBox.boxImageUrl,
            contentDescription = homeBox.title,
        )
        Spacer(height = 12.dp)
        Text(
            text = Strings.BOX_ADD_INFO_SENDER + homeBox.sender,
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.purple500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(height = 4.dp)
        Text(
            text = homeBox.title,
            style = PackyTheme.typography.body05,
            color = PackyTheme.color.gray900,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GiftBoxesTitle(
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    Row(
        modifier
    ) {
        Text(
            text = Strings.HOME_GIFT_BOXES,
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900,
        )
        Spacer(1f)
        Text(
            modifier = Modifier.clickableWithoutRipple { onMoreClick() },
            text = Strings.MORE,
            style = PackyTheme.typography.body03,
            color = PackyTheme.color.gray900,
        )
    }
}