package com.example.giftbox.boxdetailopen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.theme.PackyTheme
import com.packy.core.widget.giftbox.TopBoxPartImage

@Composable
fun GiftBoxDetailOpenScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GiftBoxDetailOpenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var boxPartAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel) {
        boxPartAnimation = true
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxDetailOpenEffect.MoveToBack -> navController.popBackStack()
                GiftBoxDetailOpenEffect.GiftBoxClose -> navController.popBackStack(
                    route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH,
                    inclusive = true
                )

                is GiftBoxDetailOpenEffect.ShowGift -> TODO()
                is GiftBoxDetailOpenEffect.ShowLetter -> TODO()
                is GiftBoxDetailOpenEffect.ShowPhoto -> TODO()
            }
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900)
        ) {
            TopBoxPartImage(
                boxPartAnimation = boxPartAnimation,
                boxPartImageUrl = uiState.giftBox?.box?.boxPart
            )
        }
    }
}