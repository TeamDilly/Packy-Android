package com.packy.core.widget.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.packy.core.theme.PackyTheme.color
import com.packy.core.theme.PackyTheme.typography

@Composable
fun PackyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        if (label != null) {
            Text(
                text = label,
                style = typography.body04,
                color = color.gray800,
            )
            Spacer(modifier = Modifier.height(height = 4.dp))
        }
        TextField(
            modifier = Modifier,
            shape = RoundedCornerShape(8.dp),
            value = value,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.colors(
                focusedTextColor = color.gray900,
                unfocusedTextColor = color.gray900,
                cursorColor = color.gray900,
                disabledPlaceholderColor = color.gray400,
                focusedPlaceholderColor = color.gray400,
                unfocusedPlaceholderColor = color.gray400
            ),
        )
    }
}