package com.packy.createbox.boxchoice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.createboax.addlatter.CreateBoxLatterIntent
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxChoiceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxChoiceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    viewModel.emitIntent(BoxChoiceIntent.OnBackClick)
                }
                .endIconButton(
                    icon = R.drawable.cancle
                ) {
                    viewModel.emitIntent(BoxChoiceIntent.OnCloseClick)
                }
                .build(
                    modifier = Modifier.padding(top = 12.dp)
                )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(height = 48.dp)
            Text(
                text = Strings.BOX_CHOICE_TITLE,
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900
            )
            Spacer(height = 48.dp)
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 75.dp)
                    .aspectRatio(1f),
                model = uiState.selectedBox?.boxTopUri,
                contentDescription = "Selected Box"
            )
            Spacer(height = 40.dp)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {

            }
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                style = buttonStyle.large.black,
                text = Strings.SAVE,
                enabled = uiState.selectedBox != null
            ) {
                viewModel.emitIntentThrottle(BoxChoiceIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}