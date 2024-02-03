package com.example.giftbox.boxdetailopen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.common.Spacer
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.giftbox.GiftBoxTopBar
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
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900)
        ) {
            TopBoxPartImage(
                modifier = Modifier.align(Alignment.TopEnd),
                boxPartAnimation = boxPartAnimation,
                boxPartImageUrl = uiState.giftBox?.box?.boxPart
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(height = 8.dp)
                GiftBoxTopBar(
                    title = "${Strings.BOX_ADD_INFO_SENDER} ${uiState.giftBox?.senderName}",
                    onBackClick = { viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnBackClick) },
                    rightButton = {
                        PackyCloseIconButton(style = closeIconButtonStyle.large.white) {
                            viewModel.emitIntentThrottle(GiftBoxDetailOpenIntent.OnCloseClick)
                        }
                    }
                )
            }
        }
    }
}