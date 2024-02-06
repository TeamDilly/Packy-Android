package com.example.home.mybox

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.packy.core.common.NoRippleTheme
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.getbox.GiftBox
import com.packy.domain.model.home.HomeBox
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyBoxScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    viewModel: MyBoxViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sendBox: LazyPagingItems<HomeBox> =
        viewModel.uiState.map { it.receiveBox }.collectAsLazyPagingItems()
    val receiveBox: LazyPagingItems<HomeBox> =
        viewModel.uiState.map { it.sendBox }.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyBoxEffect.MoveToBack -> navController.popBackStack()
                is MyBoxEffect.MoveToBoxDetail -> moveToBoxDetail(effect.boxId)
            }
        }
    }

    LaunchedEffect(uiState.showTab) {
        viewModel.uiState
            .map { it.showTab }
            .filter { it.ordinal != pagerState.pageCount }
            .collect {
                pagerState.animateScrollToPage(it.ordinal)
            }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.emitIntent(MyBoxIntent.ChangeShowBoxType(MyBoxType.entries[page]))
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    viewModel.emitIntent(MyBoxIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            MyBoxTab(
                selectedTab = uiState.showTab,
                onClick = viewModel::emitIntent
            )
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {
                when (it) {
                    MyBoxType.SEND.ordinal -> MyBoxList(
                        modifier = Modifier.fillMaxSize(),
                        boxes = sendBox,
                        moveToCreateBox = moveToCreateBox,
                        moveToBoxDetail = moveToBoxDetail,
                        emptyText = Strings.HOME_MY_BOX_EMPTY_SEND_BOX
                    )

                    MyBoxType.RECEIVE.ordinal -> MyBoxList(
                        modifier = Modifier.fillMaxSize(),
                        boxes = receiveBox,
                        moveToCreateBox = moveToCreateBox,
                        moveToBoxDetail = moveToBoxDetail,
                        emptyText = Strings.HOME_MY_BOX_EMPTY_RECEIVE_BOX
                    )
                }
            }
        }
    }
}

@Composable
private fun MyBoxList(
    modifier: Modifier = Modifier,
    boxes: LazyPagingItems<HomeBox>,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    emptyText: String,
) {
    val state: LazyGridState = rememberLazyGridState()

    if (boxes.itemCount == 0) {
        EmptyMyBoxes(
            modifier = modifier,
            emptyText = emptyText,
            moveToCreateBox = moveToCreateBox
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            state = state,
            columns = GridCells.Fixed(3),
        ) {
            items(boxes.itemCount) { index ->

            }
        }
    }
}

@Composable
private fun EmptyMyBoxes(
    modifier: Modifier,
    emptyText: String,
    moveToCreateBox: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = emptyText,
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900
        )
        Spacer(height = 4.dp)
        Text(
            text = Strings.HOME_MY_BOX_EMPTY_NUDGE,
            style = PackyTheme.typography.body02,
            color = PackyTheme.color.gray600
        )
        Spacer(height = 24.dp)
        Row(
            modifier = Modifier
                .background(
                    color = PackyTheme.color.purple500,
                    shape = CircleShape
                )
                .padding(
                    horizontal = 24.dp,
                    vertical = 17.dp
                )
                .clickableWithoutRipple(onClick = moveToCreateBox),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = Strings.HOME_CREATE_BUTTON,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.white
            )
            Spacer(width = 8.dp)
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Add",
                tint = PackyTheme.color.white
            )
        }
    }
}

@Composable
private fun MyBoxTab(
    selectedTab: MyBoxType,
    onClick: emitMviIntent<MyBoxIntent>
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = PackyTheme.color.gray900,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal])
            )
        },
    ) {
        MyBoxType.entries.forEach { tabType ->
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                val isSelected = selectedTab == tabType
                Tab(
                    text = {
                        Text(
                            text = tabType.title,
                            style = if (isSelected) PackyTheme.typography.body01 else PackyTheme.typography.body02,
                            color = if (isSelected) PackyTheme.color.gray900 else PackyTheme.color.gray600,
                        )
                    },
                    selected = isSelected,
                    onClick = { onClick(MyBoxIntent.ChangeShowBoxType(tabType)) },
                )
            }
        }
    }
}