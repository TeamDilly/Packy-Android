package com.packy.core.widget.giftbox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@Composable
fun GiftBoxTopBar(
    title: String,
    showBackArrow: Boolean = true,
    onBackClick: () -> Unit,
    rightButton: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(showBackArrow) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = PackyTheme.color.white,
                        shape = CircleShape
                    )
                    .clickableWithoutRipple {
                        onBackClick()
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(24.dp),
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "back guide screen"
                )
            }
        }
        Spacer(width = 16.dp)
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = PackyTheme.typography.body02.copy(
                textAlign = TextAlign.Start
            ),
            color = PackyTheme.color.white
        )
        Spacer(width = 16.dp)
        rightButton()
    }
}