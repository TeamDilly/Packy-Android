package com.example.home.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R
import kotlinx.coroutines.flow.callbackFlow

@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getSetting(
        )
    }

    Scaffold(
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
            Spacer(height = 24.dp)
            Profile(
                modifier = modifier.fillMaxWidth(),
                imageUrl = { uiState.profileImage ?: "" },
                nickName = { uiState.profileName ?: "" }
            )
            Spacer(height = 24.dp)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Profile(
    imageUrl: () -> String,
    nickName: () -> String,
    modifier: Modifier = Modifier
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
    }
}