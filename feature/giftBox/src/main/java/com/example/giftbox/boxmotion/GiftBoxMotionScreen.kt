package com.example.giftbox.boxmotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.domain.model.getbox.GiftBox
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiftBoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    giftBox: GiftBox
) {
    LaunchedEffect(null) {
        delay(1000)
        navController.navigate(GiftBoxRoute.getGiftBoxDetailOpenRoute(giftBox)){
            popUpTo(GiftBoxRoute.GIFT_BOX_MOTION) {
                inclusive = true
            }
        }
    }
    Scaffold { innerPadding->
        Box(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()){
            GlideImage(
                modifier = Modifier.align(Alignment.Center),
                model = giftBox.box.boxNormal,
                contentDescription = "boxFull image"
            )
        }
    }
}