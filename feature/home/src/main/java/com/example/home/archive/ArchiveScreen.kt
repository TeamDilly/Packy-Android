package com.example.home.archive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {

        }
    }
}