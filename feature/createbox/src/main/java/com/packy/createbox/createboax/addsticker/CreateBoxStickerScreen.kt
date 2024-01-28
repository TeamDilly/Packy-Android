package com.packy.createbox.createboax.addsticker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateBoxStickerScreen(
    stickerIndex: Int,
    selectedSticker: SelectedSticker,
    closeBottomSheet: () -> Unit,
    onSaveSticker: (Sticker?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CreateBoxStickerViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val stickerPagingItems: LazyPagingItems<Sticker> =
        viewModel.uiState.map { it.stickerList }.collectAsLazyPagingItems()
    val state: LazyGridState = rememberLazyGridState()

    LaunchedEffect(null) {
        viewModel.initState(
            stickerIndex,
            selectedSticker
        )
        viewModel.getSticker(selectedSticker)
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CreateBoxStickerEffect.OnSaveSticker -> {
                    closeBottomSheet()
                }

                is CreateBoxStickerEffect.OnChangeSticker -> {
                    onSaveSticker(effect.sticker)
                    closeBottomSheet()
                }
            }
        }
    }

    LazyVerticalGrid(
        modifier = Modifier
            .height(290.dp)
            .background(
                color = PackyTheme.color.white,
            )
            .padding(horizontal = 24.dp),
        state = state,
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(3) }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 24.dp,
                    ),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Strings.CREATE_BOX_STICKER_TITLE,
                        style = PackyTheme.typography.heading01,
                        color = PackyTheme.color.gray900
                    )
                    Spacer(1f)
                    Text(
                        modifier = Modifier.clickableWithoutRipple {
                            viewModel.emitIntent(CreateBoxStickerIntent.OnSaveClick)
                        },
                        text = Strings.CONFIRM,
                        style = PackyTheme.typography.body02,
                        color = PackyTheme.color.gray900
                    )
                }
                Spacer(height = 4.dp)
                Text(
                    text = Strings.CREATE_BOX_STICKER_DESCRIPTION,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.gray600
                )
            }
        }
        uiState.selectedSticker?.sticker1?.let { sticker ->
            item {
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                    stickerIndex = viewModel.isStickerSelected(sticker) ?: 0
                )
            }
        }
        uiState.selectedSticker?.sticker2?.let { sticker ->
            item {
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                    stickerIndex = viewModel.isStickerSelected(sticker) ?: 0
                )
            }
        }
        items(stickerPagingItems.itemCount) { index ->
            stickerPagingItems[index]?.let { sticker ->
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                    stickerIndex = viewModel.isStickerSelected(sticker) ?: 0
                )
            }

        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun StickerForm(
    onClick: emitMviIntent<CreateBoxStickerIntent>,
    isStickerSelected: Boolean,
    sticker: Sticker,
    stickerIndex: Int
) {
    Box(
        modifier = Modifier
            .size(106.dp)
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
            .clickableWithoutRipple {
                onClick(
                    CreateBoxStickerIntent.OnStickerClick(
                        sticker = sticker
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {

        GlideImage(
            modifier = Modifier.fillMaxSize(),
            model = sticker.imgUrl,
            contentDescription = "sticker image",
            contentScale = ContentScale.Crop
        )
        if (isStickerSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = PackyTheme.color.gray900,
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stickerIndex.toString(),
                    color = PackyTheme.color.white,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
