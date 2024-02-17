package com.example.home.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.designsystem.topbar.PackyTopBarPreview
import com.packy.core.taps.PackyBoxTap
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings.ARCHIVE_TITLE

@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }


    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startTitle(ARCHIVE_TITLE)
                .build()
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PackyTheme.color.gray100)
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.padding(start = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                ShowArchiveType.entries.forEach { type ->
                    PackyBoxTap(
                        text = { type.title },
                        isSelected = uiState.showArchiveType == type
                    ) {
                        viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveTypeClick(type))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
                items(100) {
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .fillMaxSize()
                            .background(color = PackyTheme.color.purple500)
                    )
                }
            }
        }
    }
}