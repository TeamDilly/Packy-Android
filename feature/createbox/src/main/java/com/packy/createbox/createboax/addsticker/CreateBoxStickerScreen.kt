package com.packy.createbox.createboax.addsticker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.common.conditionalBorder
import com.packy.core.designsystem.iconbutton.PackyCloseIconButton
import com.packy.core.designsystem.iconbutton.closeIconButtonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.createbox.SelectedSticker
import com.packy.domain.model.createbox.Sticker
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.flow.map

@Composable
fun CreateBoxStickerScreen(
    stickerIndex: Int,
    selectedSticker: SelectedSticker,
    closeBottomSheet: () -> Unit,
    onSaveSticker: (SelectedSticker) -> Unit,
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

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CreateBoxStickerEffect.OnSaveSticker -> {
                    onSaveSticker(effect.selectedSticker ?: selectedSticker)
                }

                is CreateBoxStickerEffect.OnChangeSticker -> {
                    onSaveSticker(effect.selectedSticker ?: selectedSticker)
                }
            }
        }
    }

    LazyVerticalGrid(
        modifier = modifier
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
            StickerTitle(closeBottomSheet)
        }
        uiState.selectedSticker?.sticker1?.let { sticker ->
            item {
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                )
            }
        }
        uiState.selectedSticker?.sticker2?.let { sticker ->
            item {
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                )
            }
        }
        items(stickerPagingItems.itemCount) { index ->
            stickerPagingItems[index]?.let { sticker ->
                StickerForm(
                    onClick = viewModel::emitIntentThrottle,
                    isStickerSelected = viewModel.isStickerSelected(sticker) != null,
                    sticker = sticker,
                )
            }
        }
        item {
            Spacer(Modifier.height(18.dp))
        }
    }
}

@Composable
private fun StickerTitle(closeBottomSheet: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = Strings.CREATE_BOX_STICKER_TITLE,
            style = PackyTheme.typography.heading01,
            color = PackyTheme.color.gray900
        )
        Spacer(1f)
        PackyCloseIconButton(style = closeIconButtonStyle.large.white) {
            closeBottomSheet()
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun StickerForm(
    modifier: Modifier = Modifier,
    isStickerSelected: Boolean,
    sticker: Sticker,
    onClick: emitMviIntent<CreateBoxStickerIntent>,
) {
    Box(
        modifier = modifier
            .background(
                color = PackyTheme.color.gray100,
                shape = RoundedCornerShape(12.dp)
            )
            .conditionalBorder(
                condition = isStickerSelected,
                shape = RoundedCornerShape(12.dp),
                width = 3.dp
            )
            .fillMaxSize()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            modifier = Modifier
                .background(
                    color = PackyTheme.color.gray100,
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxSize()
                .clickableWithoutRipple {
                    onClick(
                        CreateBoxStickerIntent.OnStickerClick(
                            sticker = sticker
                        )
                    )
                }
                .padding(13.dp),
            model = sticker.imgUrl,
            contentDescription = "sticker image",
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun StickerTitlePreview() {
    PackyTheme {
        StickerTitle(
            closeBottomSheet = {}
        )
    }
}

@Preview(widthDp = 106, heightDp = 106)
@Composable
fun StickerFormPreview() {
    PackyTheme {
        StickerForm(
            onClick = {},
            isStickerSelected = true,
            sticker = Sticker(id = 1, imgUrl = ""),
        )
    }
}

@Preview(widthDp = 106, heightDp = 106)
@Composable
fun StickerFormNonSelectedPreview() {
    PackyTheme {
        StickerForm(
            onClick = {},
            isStickerSelected = false,
            sticker = Sticker(id = 1, imgUrl = ""),
        )
    }
}