package com.example.home.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.navigation.SettingsArgs
import com.example.home.navigation.SettingsArgs.SELECTED_PROFILE_URL
import com.example.home.navigation.SettingsRoute
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.textfield.PackyTextField
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.SETTING_NICKNAME_TITLE
import com.packy.feature.core.R
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun SettingNicknameScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SettingNicknameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val saved by remember {
        derivedStateOf {
            uiState.currentNickName != "" &&
                    uiState.currentNickName != uiState.prevNickname &&
                    (uiState.currentNickName?.length ?: 0) >= 2
        }
    }

    LaunchedEffect(navController) {
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<String?>(SELECTED_PROFILE_URL, null)
            ?.filterNotNull()
            ?.collect{
                viewModel.emitIntent(SettingNicknameIntent.ChangeProfileImage(it))
            }
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingNicknameEffect.SavedNewNickName -> Unit
                SettingNicknameEffect.FailSaveNewNickName -> Unit
                SettingNicknameEffect.MoveToBack -> navController.popBackStack()
                is SettingNicknameEffect.MoveToSettingProfile -> navController.navigate(SettingsRoute.getSettingProfileRoute(effect.profileImage))
            }
        }

    }

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    viewModel.emitIntent(SettingNicknameIntent.OnBackClick)
                }
                .centerTitle(SETTING_NICKNAME_TITLE)
                .build()

        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Spacer(height = 40.dp)
            Profile(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickableWithoutRipple {
                        viewModel.emitIntentThrottle(SettingNicknameIntent.OnSettingProfileClick)
                    },
                profileImage = uiState.profileImage
            )
            Spacer(height = 40.dp)
            Text(
                text = Strings.NICKNAME,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray800,
                modifier = Modifier.padding(start = 24.dp)
            )
            Spacer(height = 8.dp)
            PackyTextField(
                value = uiState.currentNickName ?: "",
                onValueChange = {
                    viewModel.emitIntent(SettingNicknameIntent.ChangeNickName(it))
                },
                placeholder = Strings.SIGNUP_NICK_NAME_MAX_VALUE,
                trailingIconOnClick = {
                    viewModel.emitIntent(SettingNicknameIntent.ChangeNickName(""))
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                showTrailingIcon = true
            )
            Spacer(1f)
            PackyButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .imePadding()
                    .fillMaxWidth(),
                style = buttonStyle.large.black,
                text = Strings.SAVE,
                enabled = saved,
            ) {
                viewModel.emitIntent(SettingNicknameIntent.OnSaveClick)
            }
            Spacer(height = 16.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Profile(
    modifier: Modifier = Modifier,
    profileImage: String?,
) {
    Box(
        modifier = modifier
            .size(80.dp),
    ) {
        GlideImage(
            model = profileImage,
            contentDescription = "profile image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .border(
                    2.dp,
                    PackyTheme.color.white,
                    CircleShape
                )
                .background(
                    color = PackyTheme.color.gray100,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.pencil),
                contentDescription = "add profile image",
                tint = PackyTheme.color.gray900,
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.Center)
            )
        }
    }
}