package com.example.home.profileimage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.navigation.SettingsArgs
import com.example.home.nickname.SettingNicknameIntent
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.feature.core.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SettingsProfileImageScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: SettingsProfileImageViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val saved by remember { derivedStateOf { uiState.currentProfileImageUrl != uiState.prevProfileImageUrl } }

    LaunchedEffect(viewModel) {
        viewModel.initProfile()
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsProfileImageEffect.MoveToBack -> {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        SettingsArgs.PROFILE_URL,
                        uiState.currentProfileImageUrl
                    )
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.arrow_left) {
                    viewModel.emitIntent(SettingsProfileImageIntent.OnBackClick)
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(height = 60.dp)
            GlideImage(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape),
                model = uiState.currentProfileImageUrl,
                contentDescription = "profile image"
            )
            Spacer(height = 40.dp)
            LazyRow(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(uiState.profiles) { profile ->
                    GlideImage(
                        modifier = Modifier
                            .background(
                                PackyTheme.color.gray100,
                                shape = CircleShape
                            )
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickableWithoutRipple {
                                viewModel.emitIntentThrottle(
                                    SettingsProfileImageIntent.OnChangeProfile(
                                        newProfileImageUrl = profile.imageUrl
                                    )
                                )
                            },
                        model = profile.imageUrl,
                        contentDescription = "Profile Image"
                    )
                }
            }
            Spacer(1f)
            PackyButton(
                enabled = saved,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = buttonStyle.large.black,
                text = Strings.CONFIRM
            ) {
                viewModel.emitIntentThrottle(SettingsProfileImageIntent.OnSaveClick)
            }
            Spacer(16.dp)
        }
    }
}