package com.example.home.mybox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout
import com.packy.core.common.NoRippleTheme
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

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

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyBoxEffect.MoveToBack -> navController.popBackStack()
                is MyBoxEffect.MoveToBoxDetail -> moveToBoxDetail(effect.boxId)
            }
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
            TabRow(
                selectedTabIndex = uiState.showTab.ordinal,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = PackyTheme.color.gray900,
                        modifier = Modifier.tabIndicatorOffset(tabPositions[uiState.showTab.ordinal])
                    )
                },
            ) {
                MyBoxType.entries.forEach { tabType ->
                    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                        val isSelected = uiState.showTab == tabType
                        Tab(
                            text = {
                                Text(
                                    text = tabType.title,
                                    style = if (isSelected) PackyTheme.typography.body01 else PackyTheme.typography.body02,
                                    color = if (isSelected) PackyTheme.color.gray900 else PackyTheme.color.gray600,
                                )
                            },
                            selected = isSelected,
                            onClick = { viewModel.emitIntent(MyBoxIntent.ChangeShowBoxType(tabType)) },
                        )
                    }
                }
            }
        }
    }
}