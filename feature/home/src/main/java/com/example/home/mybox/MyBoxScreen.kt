package com.example.home.mybox

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ripple.LocalRippleTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.packy.core.common.NoRippleTheme
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyBoxScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    moveToBoxDetail: (Long) -> Unit,
    viewModel: MyBoxViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sendBox = remember { derivedStateOf { uiState.sendBox } }
    val receiveBox = remember { derivedStateOf { uiState.receiveBox } }

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
                    0 -> Text(text = "Send")
                    1 -> Text(text = "Receive")
                }
            }
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