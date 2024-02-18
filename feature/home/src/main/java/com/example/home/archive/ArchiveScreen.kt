package com.example.home.archive

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.home.archive.widget.ArchiveGifts
import com.example.home.archive.widget.ArchiveLetters
import com.example.home.archive.widget.ArchiveMusics
import com.example.home.archive.widget.ArchivePhotos
import com.example.home.mybox.MyBoxIntent
import com.example.home.mybox.MyBoxType
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.designsystem.topbar.PackyTopBarPreview
import com.packy.core.taps.PackyBoxTap
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings.ARCHIVE_TITLE
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.domain.model.home.HomeBox
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val photos = viewModel.uiState.map { it.photos }.collectAsLazyPagingItems()
    val gifts = viewModel.uiState.map { it.gifts }.collectAsLazyPagingItems()
    val letters = viewModel.uiState.map { it.letter }.collectAsLazyPagingItems()
    val musics = viewModel.uiState.map { it.musics }.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { ShowArchiveType.entries.size })

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }

    LaunchedEffect(viewModel) {
        viewModel.listInit()
    }

    LaunchedEffect(uiState.showArchiveType) {
        viewModel.uiState
            .map { it.showArchiveType }
            .filter { it.ordinal != pagerState.pageCount }
            .collect {
                pagerState.animateScrollToPage(it.ordinal)
            }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveTypeClick(ShowArchiveType.values()[page]))
        }
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
            ) {
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
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) { page ->
                when (page) {
                    ShowArchiveType.PHOTO.ordinal -> {
                        ArchivePhotos(
                            modifier = Modifier.fillMaxSize(),
                            photos = photos
                        )
                    }

                    ShowArchiveType.LETTER.ordinal -> {
                        ArchiveLetters(
                            modifier = Modifier.fillMaxSize(),
                            letters = letters
                        )
                    }

                    ShowArchiveType.MUSIC.ordinal -> {
                        ArchiveMusics(
                            modifier = Modifier.fillMaxSize(),
                            musics = musics
                        )
                    }

                    ShowArchiveType.GIFT.ordinal -> {
                        ArchiveGifts(
                            modifier = Modifier.fillMaxSize(),
                            gifts = gifts
                        )
                    }
                }
            }
        }
    }
}