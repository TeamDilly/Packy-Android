package com.packy.createbox.boxaddinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent

@Composable
fun BoxAddInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxAddInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    navController.popBackStack()
                }
                .build(
                    modifier = Modifier.padding(top = 12.dp)
                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(height = 24.dp)
            Text(
                text = Strings.BOX_ADD_INFO_TITLE,
                style = PackyTheme.typography.heading01.copy(textAlign = TextAlign.Center),
                color = PackyTheme.color.gray900
            )
            Spacer(height = 8.dp)
            Text(
                text = Strings.BOX_ADD_INFO_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
            Spacer(height = 37.dp)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .background(
                        color = PackyTheme.color.gray100,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                AddInfoForm(uiState.toName) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeToName(it))
                }
                AddInfoForm(uiState.fromName) {
                    viewModel.emitIntent(BoxAddInfoIntent.ChangeFromName(it))
                }
            }
        }
    }
}

@Composable
private fun AddInfoForm(
    text: String,
    onChange: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(width = 20.dp)
        Text(
            text = Strings.BOX_ADD_INFO_SENDER,
            style = PackyTheme.typography.body02,
            color = PackyTheme.color.gray900
        )
        Spacer(1f)
        PackyTextField(
            value = text,
            placeholder = Strings.BOX_ADD_INFO_PLACEHOLDER,
            textAlign = TextAlign.End,
            onValueChange = onChange,
        )
    }
}