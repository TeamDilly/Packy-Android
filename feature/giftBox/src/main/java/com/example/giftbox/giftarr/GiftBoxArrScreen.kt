package com.example.giftbox.giftarr

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.GIFT_BOX_ARR_TITLE
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiftBoxArrScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: GiftBoxArrViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                GiftBoxArrEffect.MoveToBack -> navController.popBackStack(
                    route = GiftBoxRoute.GIFT_BOX_NAV_GRAPH,
                    inclusive = true
                )

                GiftBoxArrEffect.MoveToOpenBox -> {
                    val giftBox = uiState.giftBox
                    if (giftBox != null) {
                        navController.navigate(GiftBoxRoute.getGiftBoxMotionRoute(giftBox))
                    } else {
                        // TODO
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.cancle) {
                    viewModel.emitIntentThrottle(GiftBoxArrIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(height = 48.dp)
            Text(
                text = GIFT_BOX_ARR_TITLE(uiState.giftBox?.senderName ?: ""),
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900,
            )
            Spacer(height = 20.dp)
            Text(
                modifier = Modifier
                    .border(
                        1.dp,
                        PackyTheme.color.gray300,
                        RoundedCornerShape(40.dp)
                    )
                    .background(
                        PackyTheme.color.gray100,
                        RoundedCornerShape(40.dp)
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),
                text = uiState.giftBox?.name ?: "",
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900,
            )
            Spacer(height = 64.dp)
            GlideImage(
                modifier = Modifier.size(240.dp),
                model = uiState.giftBox?.box?.boxFull,
                contentDescription = "BoxImage"
            )
            Spacer(1f)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = Strings.OPEN,
                style = buttonStyle.large.black,
                onClick = {
                    viewModel.emitIntentThrottle(GiftBoxArrIntent.OnOpenClick)
                }
            )
            Spacer(height = 16.dp)
        }
    }
}