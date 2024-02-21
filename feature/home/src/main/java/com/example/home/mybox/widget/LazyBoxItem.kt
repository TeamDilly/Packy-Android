package com.example.home.mybox.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.R
import com.packy.common.authenticator.ext.toFormatTimeStampString
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.getbox.GiftBox
import com.packy.domain.model.home.LazyBox

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LazyBoxItem(
    lazyBox: LazyBox,
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit = {},
    onMoreClick: (Long) -> Unit = {}
) {
    Row(
        modifier = modifier
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(17.dp)
            .clickableWithoutRipple {
                onClick(lazyBox.boxId)
            },
    ) {
        GlideImage(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = lazyBox.boxImageUrl,
            contentDescription = ""
        )
        Spacer(12.dp)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 1.dp)
                .height(70.dp)
        ) {
            Spacer(4.dp)
            Text(
                text = Strings.TO + lazyBox.receiverName,
                style = PackyTheme.typography.body06,
                color = PackyTheme.color.purple500
            )
            Spacer(4.dp)
            Text(
                text = lazyBox.boxTitle,
                style = PackyTheme.typography.body03,
                color = PackyTheme.color.gray900
            )
            Spacer(1f)
            Text(
                text = lazyBox.createdAt.toFormatTimeStampString(),
                style = PackyTheme.typography.body06,
                color = PackyTheme.color.gray600
            )
            Spacer(4.dp)
        }
        Icon(
            imageVector = ImageVector.vectorResource(id = com.packy.feature.core.R.drawable.ellipsis),
            tint = PackyTheme.color.gray900,
            contentDescription = null,
            modifier = Modifier.clickableWithoutRipple { onMoreClick(lazyBox.boxId) },
        )
    }
}

@Composable
@Preview
fun LazyBoxItemPreview() {
    PackyTheme {
        LazyBoxItem(
            lazyBox = LazyBox(
                boxImageUrl = "",
                boxTitle = "보내지 않은 박스",
                boxId = 1,
                createdAt = "2024-02-21T21:20:44.818648",
                receiverName = "누군가보낸사람"
            )
        )
    }
}