package com.example.home.archive

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.archive.widget.ArchiveGifts
import com.example.home.archive.widget.ArchiveLetters
import com.example.home.archive.widget.ArchiveMusics
import com.example.home.archive.widget.ArchivePhotos
import com.example.home.mybox.MyBoxIntent
import com.example.home.mybox.MyBoxType
import com.packy.common.authenticator.ext.colorCodeToColor
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.designsystem.topbar.PackyTopBarPreview
import com.packy.core.taps.PackyBoxTap
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings.ARCHIVE_TITLE
import com.packy.core.widget.giftbox.PhotoForm
import com.packy.core.widget.youtube.YoutubePlayer
import com.packy.core.widget.youtube.YoutubeState
import com.packy.domain.model.archive.ArchiveGift
import com.packy.domain.model.archive.ArchiveLetter
import com.packy.domain.model.archive.ArchiveMusic
import com.packy.domain.model.archive.ArchivePhoto
import com.packy.domain.model.home.HomeBox
import com.packy.lib.ext.extractYouTubeVideoId
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

@OptIn(
    ExperimentalFoundationApi::class,
)
@Composable
fun ArchiveScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ArchiveViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showArchive by remember {
        derivedStateOf { uiState.showArchive }
    }

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
            viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveTypeClick(ShowArchiveType.entries[page]))
        }
    }
    when (showArchive) {
        is ShowArchive.ShowArchivePhoto -> {
            val photo = (showArchive as ShowArchive.ShowArchivePhoto).archivePhoto
            PhotoDialog(
                photo = photo
            ) {
                viewModel.emitIntentThrottle(ArchiveIntent.CloseArchive)
            }
        }

        is ShowArchive.ShowArchiveLetter -> {
            val letter = (showArchive as ShowArchive.ShowArchiveLetter).archiveLetter
            LetterDialog(
                letter = letter
            ) {
                viewModel.emitIntentThrottle(ArchiveIntent.CloseArchive)
            }
        }

        is ShowArchive.ShowArchiveMusic -> {
            val music = (showArchive as ShowArchive.ShowArchiveMusic).archiveMusic
            MusicDialog(
                music = music
            ) {
                viewModel.emitIntentThrottle(ArchiveIntent.CloseArchive)
            }
        }

        is ShowArchive.ShowArchiveGift -> {
            val gift = (showArchive as ShowArchive.ShowArchiveGift).archiveGift
            GiftDialog(
                gift = gift
            ) {
                viewModel.emitIntentThrottle(ArchiveIntent.CloseArchive)
            }
        }

        is ShowArchive.NonType -> Unit
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startTitle(ARCHIVE_TITLE)
                .build()
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Spacer(modifier = Modifier.height(7.dp))
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PackyTheme.color.gray100)
                .padding(innerPadding)
        ) {
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
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
                state = pagerState
            ) { page ->
                when (page) {
                    ShowArchiveType.PHOTO.ordinal -> {
                        ArchivePhotos(
                            modifier = Modifier.fillMaxSize(),
                            photos = photos,
                            onClick = {
                                viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveClick(ShowArchive.ShowArchivePhoto(it)))
                            }
                        )
                    }

                    ShowArchiveType.LETTER.ordinal -> {
                        ArchiveLetters(
                            modifier = Modifier.fillMaxSize(),
                            letters = letters,
                            onClick = {
                                viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveClick(ShowArchive.ShowArchiveLetter(it)))
                            }
                        )
                    }

                    ShowArchiveType.MUSIC.ordinal -> {
                        ArchiveMusics(
                            modifier = Modifier.fillMaxSize(),
                            musics = musics,
                            onClick = {
                                viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveClick(ShowArchive.ShowArchiveMusic(it)))
                            }
                        )
                    }

                    ShowArchiveType.GIFT.ordinal -> {
                        ArchiveGifts(
                            modifier = Modifier.fillMaxSize(),
                            gifts = gifts,
                            onClick = {
                                viewModel.emitIntentThrottle(ArchiveIntent.OnArchiveClick(ShowArchive.ShowArchiveGift(it)))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PhotoDialog(
    photo: ArchivePhoto,
    close: () -> Unit,
) {
    Dialog(onDismissRequest = { close() }) {
        Column(
            modifier = Modifier
                .width(312.dp)
                .height(374.dp)
                .padding(16.dp)
                .background(PackyTheme.color.gray200)
                .clickableWithoutRipple {
                    close()
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(280.dp),
                model = photo.photoUrl,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Text(
                text = photo.photoContentText,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.gray900,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
            )
        }
    }
}

@Composable
fun LetterDialog(
    letter: ArchiveLetter,
    close: () -> Unit,
) {
    Dialog(onDismissRequest = close) {
        Box(
            modifier = Modifier
                .width(342.dp)
                .height(300.dp)
                .background(
                    color = PackyTheme.color.gray100,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = PackyTheme.color.gray200,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 1.dp,
                    color = letter.borderColor.colorCodeToColor(
                        fallbackColor = PackyTheme.color.gray100,
                        alpha = letter.borderOpacity
                            .toFloat()
                            .times(0.01f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickableWithoutRipple {
                    close()
                }
        ) {
            Text(
                modifier = Modifier
                    .padding(20.dp),
                text = letter.letterContent,
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900
            )
        }
    }
}

@Composable
fun MusicDialog(
    music: ArchiveMusic,
    close: () -> Unit,
) {
    Dialog(onDismissRequest = close) {
        extractYouTubeVideoId(music.youtubeUrl)?.let { url ->
            YoutubePlayer(
                videoId = url,
                youtubeState = YoutubeState.PLAYING,
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiftDialog(
    gift: ArchiveGift,
    close: () -> Unit,
) {
    Dialog(onDismissRequest = close) {
        GlideImage(
            modifier = Modifier
                .clickableWithoutRipple {
                    close()
                }
                .padding(
                    horizontal = 55.dp,
                    vertical = 140.dp
                ),
            model = gift.giftUrl,
            contentDescription = "",
            contentScale = ContentScale.Fit
        )
    }
}