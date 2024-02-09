package com.packy.createbox.createboax.boxchange

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.box.BoxDesign

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateBoxChangeScreen(
    modifier: Modifier = Modifier,
    currentBox: BoxDesign,
    onSaveBox: (BoxDesign) -> Unit,
    closeBoxChange: () -> Unit,
    viewModel: CreateBoxChangeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(null) {
        viewModel.getBoxDesign(currentBox)
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxChangeEffect.OnConfirmClick -> {
                    closeBoxChange()
                }

                is CreateBoxChangeEffect.ChangeBox -> {
                   onSaveBox(effect.box)
                }
            }
        }
    }

    BackHandler(true) {
        closeBoxChange()
    }

    Column {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    top = 24.dp,
                )
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.CREATE_BOX_CHANGE_BOX_TITLE,
                    style = PackyTheme.typography.heading01,
                    color = PackyTheme.color.gray900
                )
                Spacer(1f)
                Text(
                    modifier = Modifier.clickableWithoutRipple {
                        viewModel.emitIntent(CreateBoxChangeIntent.OnConfirmClick)
                    },
                    text = Strings.CONFIRM,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900
                )
            }
            Spacer(height = 4.dp)
            Text(
                text = Strings.CREATE_BOX_CHANGE_BOX_DESCRIPTION,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
        }
        Spacer(height = 24.dp)
        LazyRow(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
            contentPadding = PaddingValues(horizontal = 40.dp)
        ) {
            items(uiState.boxDesignList) { boxDesign ->
                GlideImage(
                    modifier = modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickableWithoutRipple {
                            viewModel.emitIntentThrottle(
                                CreateBoxChangeIntent.ChangeBox(
                                    boxDesign
                                )
                            )
                        },
                    model = boxDesign.boxSmall,
                    contentDescription = "Box",
                )
            }
        }
        Spacer(height = 40.dp)
    }
}