package com.packy.createbox.createboax.addsticker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.domain.model.createbox.Sticker
import java.util.concurrent.Flow

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateBoxStickerScreen(
    stickerIndex: Int,
    selectedSticker: Sticker?,
    modifier: Modifier = Modifier,
    viewModel: CreateBoxStickerViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(null) {
        viewModel.getSticker()
    }

    LazyVerticalGrid(
        modifier = Modifier
            .height(290.dp)
            .background(
                color = PackyTheme.color.white,
            )
            .padding(horizontal = 24.dp),
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
        items(uiState.stickerList) { sticker ->
            Box(
                modifier = Modifier
                    .size(106.dp)
                    .background(
                        color = PackyTheme.color.gray100,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                GlideImage(
                    modifier = Modifier.fillMaxSize(),
                    model = sticker.imgUrl,
                    contentDescription = "sticker image",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}