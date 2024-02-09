package com.example.giftbox.boxerror

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings

@Composable
fun GiftBoxErrorScreen(
    modifier: Modifier = Modifier,
    closeGiftBox: () -> Unit,
    message: String,
    viewModel: GiftBoxErrorViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Spacer(1f)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = Strings.GIFT_BOX_ERROR_MESSAGE,
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900
            )
            Spacer(16.dp)
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = Strings.GIFT_BOX_ERROR_ALREADY_OPENED,
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray800
            )
            Spacer(1f)
            PackyButton(
                style = buttonStyle.large.black,
                text = Strings.CONFIRM
            ) {
                closeGiftBox()
            }
            Spacer(24.dp)
        }
    }
}