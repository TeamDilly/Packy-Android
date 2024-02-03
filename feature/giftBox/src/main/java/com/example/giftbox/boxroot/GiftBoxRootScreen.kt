package com.example.giftbox.boxroot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.theme.PackyTheme

@Composable
fun GiftBoxRootScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    boxId: String,
    viewModel: GiftBoxRootViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.getGiftBox(boxId)
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxRootEffect.FailToGetGIftBox -> navController.navigate(GiftBoxRoute.GIFT_BOX_ERROR) {
                    popUpTo(GiftBoxRoute.GIFT_BOX_ROOT) {
                        inclusive = true
                    }
                }

                GiftBoxRootEffect.GetGiftBox -> navController.navigate(GiftBoxRoute.GIFT_BOX_ARR) {
                    popUpTo(GiftBoxRoute.GIFT_BOX_ROOT) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(color = PackyTheme.color.white)
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}