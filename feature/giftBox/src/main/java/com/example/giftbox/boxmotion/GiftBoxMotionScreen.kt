package com.example.giftbox.boxmotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.domain.model.getbox.GiftBox

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiftBoxMotionScreen(
    modifier: Modifier = Modifier,
    giftBox: GiftBox
) {
    LaunchedEffect(null) {

    }
    Scaffold { innerPadding->
        Box(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()){
            GlideImage(
                modifier = Modifier.align(Alignment.Center),
                model = giftBox.box.boxFull,
                contentDescription = "boxFull image"
            )
        }
    }
}