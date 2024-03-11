package com.example.home.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.navigation.SettingsRoute
import com.example.home.navigation.SettingsRoute.SETTING_ACCOUNT
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.page.navigation.CommonRoute
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.MODIFY_PROFILE
import com.packy.di.BuildConfig
import com.packy.domain.model.settings.SettingItem
import com.packy.feature.core.R
import com.packy.lib.ext.toEncoding
import com.packy.mvi.ext.emitMviIntent

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    logout: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    var logOutDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSetting()
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsEffect.Logout -> {
                    logOutDialog = true
                }

                SettingsEffect.MoveToAccountManage -> navController.navigate(SETTING_ACCOUNT)
                SettingsEffect.MoveToBack -> navController.popBackStack()
                is SettingsEffect.MoveToWeb -> {
                    navController.navigate(
                        route = CommonRoute.getWebScreenRoute(effect.url.toEncoding())
                    )
                }

                is SettingsEffect.MoveToModifyProfile -> {
                    navController.navigate(
                        route = SettingsRoute.getSettingNicknameRoute(
                            profileUrl = effect.profileImage ?: "",
                            nickname = effect.profileName ?: ""
                        )
                    )
                }
            }
        }
    }

    if (logOutDialog) {
        PackyDialog(
            title = Strings.LOGOUT_DIALOG_TITLE,
            dismiss = Strings.CANCEL,
            confirm = Strings.LOGOUT,
            onConfirm = {
                logOutDialog = false
                logout()
            },
            onDismiss = { logOutDialog = false },
            backHandler = { logOutDialog = false }
        )
    }

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    viewModel.emitIntent(SettingsIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Spacer(height = 12.dp)
            Profile(
                modifier = modifier.fillMaxWidth(),
                imageUrl = { uiState.profileImage ?: "" },
                nickName = { uiState.profileName ?: "" },
                modifyProfile = { viewModel.emitIntentThrottle(SettingsIntent.OnModifyProfileClick) }
            )
            Spacer(height = 12.dp)
            SettingItem(
                title = Strings.ACCOUNT_MANAGE,
                onClick = { viewModel.emitIntentThrottle(SettingsIntent.OnAccountManageClick) }
            )
            SettingsDivier()
            ServerSettingItem(
                settings = { uiState.settings },
                onClick = viewModel::emitIntentThrottle
            )
            Row(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = Strings.VERSION,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900
                )
                Spacer(8.dp)
                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.gray600
                )
            }
            SettingsDivier()
            SettingItem(
                title = Strings.LOGOUT,
                onClick = { viewModel.emitIntentThrottle(SettingsIntent.OnLogoutClick) },
                iconVisible = false
            )
        }
    }
}

@Composable
private fun ColumnScope.SettingsDivier() {
    Spacer(height = 12.dp)
    Divider(
        modifier = Modifier.padding(horizontal = 24.dp),
        color = PackyTheme.color.gray200
    )
    Spacer(height = 12.dp)
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Profile(
    modifier: Modifier = Modifier,
    imageUrl: () -> String,
    nickName: () -> String,
    modifyProfile: () -> Unit = {},
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        GlideImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = imageUrl(),
            contentDescription = null
        )
        Spacer(width = 16.dp)
        Text(
            text = nickName(),
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(1f)
        Text(
            text = MODIFY_PROFILE,
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.gray900,
            modifier = Modifier
                .background(
                    PackyTheme.color.gray100,
                    CircleShape
                )
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
                .clickableWithoutRipple { modifyProfile() }
        )
    }
}

@Composable
private fun SettingItem(
    modifier: Modifier = Modifier,
    title: String,
    onClick: (() -> Unit)?,
    iconVisible: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            )
            .clickableWithoutRipple {
                onClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = PackyTheme.typography.body02,
            color = PackyTheme.color.gray900,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(1f)
        if (onClick != null && iconVisible) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrow_right),
                contentDescription = null,
                tint = PackyTheme.color.gray600
            )
        }
    }
}

@Composable
private fun ServerSettingItem(
    modifier: Modifier = Modifier,
    settings: () -> List<SettingItem>,
    onClick: emitMviIntent<SettingsIntent>,
) {
    settings.invoke().forEach { settingItem ->
        SettingItem(
            modifier = modifier,
            title = settingItem.route.title,
            onClick = { onClick(SettingsIntent.OnWebSettingClick(settingItem.url)) }
        )
    }
}
