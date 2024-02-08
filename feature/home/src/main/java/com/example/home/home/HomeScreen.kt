package com.example.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
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
    val giftBoxes by remember {
        derivedStateOf { uiState.giftBoxes }
    }

    LaunchedEffect(Unit) {
        viewModel.getGiftBoxes()
        viewModel.resetPoint()
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
        ) {
            Spacer(height = 16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = PackyTheme.color.gray900,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 36.dp),
                    text = Strings.HOME_CREATE_BOX_TITLE,
                    style = PackyTheme.typography.body01,
                    color = PackyTheme.color.white,
                    textAlign = TextAlign.Center,
                )
                Image(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(154.dp),
                    painter = painterResource(id = com.example.home.R.drawable.home_create_box),
                    contentDescription = "create box",
                )
                Box(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .background(
                            color = PackyTheme.color.white,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickableWithoutRipple { viewModel.emitIntentThrottle(HomeIntent.OnCrateBoxClick) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 14.dp),
                        text = Strings.HOME_CREATE_BUTTON,
                        style = PackyTheme.typography.body04,
                        color = PackyTheme.color.gray900
                    )
                }
            }
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
                    onMoreClick = { viewModel.emitIntentThrottle(HomeIntent.OnMoreBoxClick) },
                    moveToBoxDetail = { viewModel.emitIntentThrottle(HomeIntent.OnBoxDetailClick(it)) }
                )
                Spacer(height = 24.dp)
            }
        }
    }
}

@Composable
private fun HomeGiftBoxes(
    modifier: Modifier = Modifier,
    giftBoxes: List<HomeBox>,
    onMoreClick: () -> Unit = {},
    moveToBoxDetail: (Long) -> Unit,
) {
    GiftBoxesTitle(
        modifier = modifier
            .padding(24.dp),
        onMoreClick = onMoreClick
    )
    LazyRow(
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(giftBoxes) { homeBox ->
            GiftBoxItem(
                modifier = Modifier
                    .width(120.dp),
                homeBox = homeBox,
                moveToBoxDetail = moveToBoxDetail
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GiftBoxItem(
    modifier: Modifier = Modifier,
    homeBox: HomeBox,
    moveToBoxDetail: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .clickableWithoutRipple { moveToBoxDetail(homeBox.boxId) }
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
            style = PackyTheme.typography.body03,
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