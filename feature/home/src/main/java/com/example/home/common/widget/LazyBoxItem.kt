package com.example.home.common.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.domain.model.getbox.GiftBox

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LazyBoxItem(
    modifier: Modifier = Modifier,
    giftBox: GiftBox,
    onClick: (Long) -> Unit
) {
    Row(
        modifier = modifier,
    ) {
        GlideImage(
            modifier = Modifier.size(70.dp),
            model = giftBox.box.boxNormal,
            contentDescription = ""
        )
        Column {

        }
    }
}