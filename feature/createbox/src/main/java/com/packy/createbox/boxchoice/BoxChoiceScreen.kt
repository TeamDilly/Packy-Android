package com.packy.createbox.boxchoice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.ValueChangeAnimation
import com.packy.createbox.navigation.CreateBoxScreens
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxChoiceScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeCreateBox: () -> Unit,
    viewModel: BoxChoiceViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.BOX_CHOICE_BOX)
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBoxDesign()
    }

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                BoxChoiceEffect.CloseCreateBox -> closeCreateBox()
                BoxChoiceEffect.MoveToBack -> {
                    navController.popBackStack()
                }

                is BoxChoiceEffect.SaveBoxInfo -> {
                    val boxDesign = effect.boxDesign?.boxLottie
                    if (effect.shouldShowBoxMotion && boxDesign != null) {
                        navController.navigate(
                            CreateBoxScreens.BoxMotion.create(boxDesign)
                        )
                    } else {
                        navController.navigate(CreateBoxScreens.BoxGuidePaging.name) {
                            launchSingleTop = true
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
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
                .windowInsetsPadding(WindowInsets.statusBars)
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
            Spacer(height = 60.dp)
            ValueChangeAnimation(value = uiState.selectedBox?.boxSet) {
                GlideImage(
                    modifier = Modifier
                        .width(232.dp)
                        .height(260.dp),
                    model = it,
                    contentScale = ContentScale.Fit,
                    contentDescription = "Selected Box"
                )
            }
            Spacer(height = 40.dp)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item { Spacer(width = 40.dp) }
                items(uiState.boxDesignList) { boxDesign ->
                    GlideImage(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickableWithoutRipple {
                                viewModel.emitIntent(BoxChoiceIntent.ChangeSelectBox(boxDesign))
                            },
                        model = boxDesign.boxSmall,
                        contentScale = ContentScale.Crop,
                        contentDescription = "Box Design"
                    )
                }
                item { Spacer(width = 40.dp) }
            }
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp),
                style = buttonStyle.large.black,
                text = Strings.NEXT,
                enabled = uiState.selectedBox != null
            ) {
                viewModel.emitIntentThrottle(BoxChoiceIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}