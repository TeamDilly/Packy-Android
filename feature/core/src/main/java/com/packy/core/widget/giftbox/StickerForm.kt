package com.packy.core.widget.giftbox

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.widget.animation.FlagChangeAnimation
import com.packy.core.widget.animation.ValueChangeAnimation
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StickerForm(
    modifier: Modifier = Modifier,
    stickerUri: String? = null,
    inclination: Float = 0f,
    onClick: () -> Unit = {}
) {
    Box(modifier = modifier.clickableWithoutRipple {
        onClick()
    }) {
        FlagChangeAnimation(
            flag = stickerUri != null,
            enter = fadeIn(
                animationSpec = tween(
                    400,
                    delayMillis = 120
                )
            ) + scaleIn(
                initialScale = 1.3f,
                animationSpec = tween(
                    400,
                    delayMillis = 120
                )
            ),
            exit = fadeOut(animationSpec = tween(durationMillis = 120)),
            flagOnContent = {
                ValueChangeAnimation(
                    value = stickerUri,
                    enterAnimation = fadeIn(
                        animationSpec = tween(
                            400,
                            delayMillis = 120
                        )
                    ) + scaleIn(
                        initialScale = 1.3f,
                        animationSpec = tween(
                            400,
                            delayMillis = 120
                        )
                    ),
                    exitAnimation = fadeOut(animationSpec = tween(durationMillis = 120))
                ) {
                    GlideImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(inclination),
                        model = it,
                        contentDescription = "sticker image",
                        contentScale = ContentScale.Crop,
                    )
                }
            },
            flagOffContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.plus_square_dashed),
                        contentDescription = "sticker placeholder icon",
                        tint = PackyTheme.color.white,
                    )
                }
            }
        )
    }
}
