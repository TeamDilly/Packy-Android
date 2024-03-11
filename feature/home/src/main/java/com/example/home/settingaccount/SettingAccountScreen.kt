package com.example.home.settingaccount

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.navigation.SettingsRoute.WITHDRAWAL
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.feature.core.R

@Composable
fun SettingAccountScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    navController.popBackStack()
                }
                .centerTitle(Strings.ACCOUNT_MANAGE)
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(12.dp)
            Row(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.CONNECTED_ACCOUT,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900
                )
                Spacer(1f)
                Text(
                    text = Strings.KAKAO,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.gray600
                )
                Spacer(4.dp)
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.kakao),
                    contentDescription = "kakao Logo",
                    tint = Color.Unspecified
                )
            }
            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                    .clickableWithoutRipple {
                        navController.navigate(WITHDRAWAL)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.WITHDRAWAL,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900
                )
                Spacer(1f)
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right),
                    contentDescription = "kakao Logo"
                )
            }
        }
    }
}