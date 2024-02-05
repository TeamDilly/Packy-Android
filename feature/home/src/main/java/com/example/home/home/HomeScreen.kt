package com.example.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.feature.core.R
import com.example.home.navigation.HomeRoute
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme

@Composable
fun HomeScreen(
    navController: NavController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
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
            }
        }
    }
}