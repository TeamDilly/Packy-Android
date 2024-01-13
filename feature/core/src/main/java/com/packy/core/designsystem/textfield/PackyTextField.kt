package com.packy.core.designsystem.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@Composable
fun PackyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    maxValues: Int = Int.MAX_VALUE,
    label: String? = null,
    showTrailingIcon: Boolean = false,
    trailingIconOnClick: (() -> Unit) = {
        onValueChange("")
    },
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        if (label != null) {
            Text(
                text = label,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray800,
            )
            Spacer(modifier = Modifier.height(height = 4.dp))
        }
        BasicTextField(
            modifier = Modifier
                .height(50.dp)
                .background(
                    color = PackyTheme.color.gray100,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            value = value,
            onValueChange = {
                onValueChange(it.take(maxValues))
            },
            textStyle = PackyTheme.typography.body04.copy(color = PackyTheme.color.gray900),
            maxLines = maxLines,
            minLines = minLines,
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (placeholder != null && value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = PackyTheme.typography.body04,
                            color = PackyTheme.color.gray400.copy(alpha = 0.5f),
                        )
                    }
                    innerTextField()
                    if (showTrailingIcon) {
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        IconButton(
                            onClick = trailingIconOnClick,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .background(
                                    color = PackyTheme.color.gray400,
                                    shape = CircleShape
                                )
                                .size(16.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(12.dp),
                                painter = painterResource(id = R.drawable.cancle),
                                contentDescription = "text field trailing icon",
                                tint = PackyTheme.color.white
                            )
                        }
                    }
                }
            }
        )
    }
}