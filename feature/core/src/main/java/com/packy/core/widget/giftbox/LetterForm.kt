package com.packy.core.widget.giftbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.theme.PackyTheme
import com.packy.lib.ext.removeNewlines


@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun LetterForm(
    letterContent: String,
    envelopeUrl: String?,
    modifier: Modifier = Modifier,
    inclination: Float = 0f,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .rotate(inclination),
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize(0.85f)
                .background(
                    color = PackyTheme.color.white,
                    shape = RoundedCornerShape(8.dp)
                )
                .align(Alignment.TopStart)
                .padding(
                    horizontal = 10.dp,
                    vertical = 6.dp
                ),
            text = letterContent.removeNewlines(),
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.gray900
        )
        GlideImage(
            modifier = Modifier
                .fillMaxSize(0.85f)
                .align(Alignment.BottomEnd),
            model = envelopeUrl,
            contentDescription = "box guide Letter",
            contentScale = ContentScale.Crop
        )
    }
}
