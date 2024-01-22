package com.packy.createbox.boxaddinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.feature.core.R

@Composable
fun BoxAddInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(
                    icon = R.drawable.arrow_left
                ) {
                    navController.popBackStack()
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {

        }
    }
}