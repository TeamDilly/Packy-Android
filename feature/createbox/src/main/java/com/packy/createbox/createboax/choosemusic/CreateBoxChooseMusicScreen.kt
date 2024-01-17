package com.packy.createbox.createboax.choosemusic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.createbox.navigation.CreateBoxRoute
import com.packy.feature.core.R

@Composable
fun CreateBoxChooseMusicScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    closeBottomSheet: () -> Unit,
    viewModel: CreateBoxChooseMusicViewModel = hiltViewModel()
) {

    LaunchedEffect(null) {
        viewModel.effect.collect { effect ->
            when (effect) {
                CreateBoxChooseMusicEffect.CloseBottomSheet -> closeBottomSheet()
                CreateBoxChooseMusicEffect.MoveToPackyMusic -> navController.navigate(
                    CreateBoxBottomSheetRoute.CREATE_BOX_ADD_YOUR_MUSIC
                )

                CreateBoxChooseMusicEffect.MoveToYourMusic -> navController.navigate(
                    CreateBoxBottomSheetRoute.CREATE_BOX_ADD_PACKY_MUSIC
                )
            }
        }
    }
    Column {
        Spacer(height = 12.dp)
        PackyTopBar.Builder()
            .endIconButton(icon = R.drawable.cancle) {
                viewModel.emitIntent(CreateBoxChooseMusicIntent.OnCloseClick)
            }
            .build()
        Spacer(height = 9.dp)

        Column(
            modifier = modifier.padding(horizontal = 24.dp),
        ) {
            Text(
                text = Strings.CRATE_BOX_MUSIC,
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900
            )
            Spacer(height = 24.dp)
            ChooseMusicBox(
                title = Strings.CHOOSE_YOUR_MUSIC_TITLE,
                description = Strings.CHOOSE_YOUR_MUSIC_DESCRIPTION,
            ) {
                viewModel.emitIntent(CreateBoxChooseMusicIntent.OnChooseYourMusicClick)
            }
            Spacer(height = 8.dp)
            ChooseMusicBox(
                title = Strings.CHOOSE_YOUR_MUSIC_TITLE,
                description = Strings.CHOOSE_YOUR_MUSIC_DESCRIPTION,
            ) {
                viewModel.emitIntent(CreateBoxChooseMusicIntent.OnPackyMusicClick)
            }
            Spacer(height = 32.dp)
        }
    }
}


@Composable
private fun ChooseMusicBox(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(8.dp)
            )
            .clickableWithoutRipple(onClick = onClick)
            .padding(all = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = title,
                style = PackyTheme.typography.body01,
                color = PackyTheme.color.gray900
            )
            Spacer(height = 2.dp)
            Text(
                text = description,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray600
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Arrow Right"
        )
    }
}

@Preview
@Composable
fun PreviewChooseMusicBox() {
    ChooseMusicBox(
        title = Strings.CHOOSE_YOUR_MUSIC_TITLE,
        description = Strings.CHOOSE_YOUR_MUSIC_DESCRIPTION,
    ) {}
}