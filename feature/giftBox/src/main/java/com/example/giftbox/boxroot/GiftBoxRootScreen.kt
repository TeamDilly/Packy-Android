package com.example.giftbox.boxroot

import android.os.Bundle
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
import com.example.giftbox.navigation.GiftBoxScreens
import com.packy.core.theme.PackyTheme

@Composable
fun GiftBoxRootScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    skipArr: Boolean,
    shouldShowShared: Boolean,
    viewModel: GiftBoxRootViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GiftBoxRootEffect.FailToGetGIftBox -> navController.navigate(
                    route = effect.giftBoxId?.let { GiftBoxScreens.GiftBoxError.create(giftBoxId = it) }
                        ?: run { GiftBoxScreens.GiftBoxError.name },
                ) {
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                }

                is GiftBoxRootEffect.GetGiftBox -> {
                    val route =
                        if (skipArr)
                            GiftBoxScreens.GiftBoxDetailOpen.create(
                                giftBox = effect.giftBox,
                                shouldShowShared = shouldShowShared
                            )
                        else GiftBoxScreens.GiftBoxArr.create(effect.giftBox)
                    navController.navigate(route) {
                        val currentRoute = navController.currentBackStackEntry?.destination?.route
                        currentRoute?.let { popUpTo(it) { inclusive = true } }
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