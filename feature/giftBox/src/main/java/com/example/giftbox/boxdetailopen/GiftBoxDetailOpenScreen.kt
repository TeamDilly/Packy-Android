package com.example.giftbox.boxdetailopen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.giftbox.navigation.GiftBoxRoute

@Composable
fun GiftBoxDetailOpenScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GiftBoxDetailOpenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
        Column(modifier = modifier.padding(innerPadding)) {
            // TODO
        }
    }
}