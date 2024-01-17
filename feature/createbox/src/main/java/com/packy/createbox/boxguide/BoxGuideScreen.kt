package com.packy.createbox.boxguide

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.CRATE_BOX_MUSIC
import com.packy.di.network.Packy
import com.packy.feature.core.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(true) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier.padding(start = 24.dp),
            ){
                Spacer(height = 12.dp)
                PackyTopBar.Builder()
                    .endIconButton(icon = R.drawable.cancle) {
                        showBottomSheet = false
                    }
                    .build()
                Spacer(height = 9.dp)
                Text(
                    text = CRATE_BOX_MUSIC,
                    style = PackyTheme.typography.heading01,
                    color = PackyTheme.color.gray900
                )
                Spacer(height = 24.dp)
                ChooseMusicBox(
                    title = Strings.CHOOSE_YOUR_MUSIC_TITLE,
                    description = Strings.CHOOSE_YOUR_MUSIC_DESCRIPTION,
                ) {}
                Spacer(height = 8.dp)
                ChooseMusicBox(
                    title = Strings.CHOOSE_YOUR_MUSIC_TITLE,
                    description = Strings.CHOOSE_YOUR_MUSIC_DESCRIPTION,
                ) {}
            }
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
            .padding(all = 24.dp)
            .clickable(onClick = onClick),
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