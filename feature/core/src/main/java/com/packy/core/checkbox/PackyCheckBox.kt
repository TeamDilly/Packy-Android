package com.packy.core.checkbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.packy.core.common.Spacer
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@Composable
fun PackyCheckBox(
    modifier: Modifier = Modifier,
    label: String? = null,
    isSelected: Boolean,
    onClick: (Boolean) -> Unit,
) {
    val checkIconColor = if (isSelected) {
        PackyTheme.color.purple500
    } else {
        PackyTheme.color.gray400
    }
    Row(
        modifier = modifier
            .padding()
            .clickable {
                onClick(!isSelected)
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.check),
            contentDescription = "CheckBoxIcon",
            tint = checkIconColor
        )
        if (label != null) {
            Spacer(width = 12.dp)
            Text(
                text = label,
                style = PackyTheme.typography.body03,
                color = PackyTheme.color.gray800
            )
        }
    }
}

@Preview
@Composable
fun EnabledCheckBox(){
    PackyCheckBox(
        isSelected = true,
    ){

    }
}

@Preview
@Composable
fun DisabledCheckBox(){
    PackyCheckBox(
        isSelected = false,
    ){

    }
}