package com.packy.createbox.boxguide

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.snackbar.PackySnackBarHost
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.COMPLETE
import com.packy.core.widget.dotted.dottedStroke
import com.packy.feature.core.R
import com.packy.createbox.createboax.addlatter.CreateBoxLatterScreen
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoScreen
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BoxGuideViewModel = hiltViewModel()
) {
    val bottomSheetState = SheetState(
        skipHiddenState = false,
        skipPartiallyExpanded = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()
    var snackBarVisible by remember { mutableStateOf(false) }

    var bottomSheetRoute by remember { mutableStateOf(BoxGuideBottomSheetRoute.ADD_MUSIC) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        snackbarHost = PackySnackBarHost,
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContent = {
            BottomSheetNav(
                bottomSheetRoute = bottomSheetRoute,
                closeBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
                showSnackbar = {
                    scope.launch {
                        if (!snackBarVisible) {
                            snackBarVisible = true
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = it,
                                duration = SnackbarDuration.Short
                            ).let {
                                snackBarVisible = false
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        BackHandler(enabled = scaffoldState.bottomSheetState.isVisible) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(PackyTheme.color.gray900),
            verticalArrangement = Arrangement.Center,
        ) {
            Spacer(height = 8.dp)
            TopBar(
                title = "To.이호이호이호",
                onBackClick = viewModel::emitIntentThrottle,
                onSaveClick = viewModel::emitIntentThrottle,
            )
            Spacer(height = 31.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BoxGuideContent(
                        modifier = Modifier
                            .aspectRatio(160f / 192f)
                            .fillMaxWidth()
                            .weight(38f),
                        inclination = -3f,
                        placeholder = {
                            BoxPlaceholder(
                                icon = R.drawable.photo,
                                title = Strings.BOX_GUIDE_PHOTO
                            )
                        }
                    )
                    Spacer(28.dp)
                    Sticker(
                        modifier = Modifier
                            .aspectRatio(1f / 1f)
                            .weight(28f),
                        inclination = 10f,
                        stickerUri = null
                    )
                }
                Spacer(height = 20.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Sticker(
                        modifier = Modifier
                            .aspectRatio(1f / 1f)
                            .weight(30f),
                        inclination = -10f,
                        stickerUri = null
                    )
                    Spacer(22.dp)
                    BoxGuideContent(
                        modifier = Modifier
                            .aspectRatio(180f / 150f)
                            .fillMaxWidth()
                            .weight(42f),
                        inclination = 3f,
                        placeholder = {
                            BoxPlaceholder(
                                icon = R.drawable.envelope,
                                title = Strings.BOX_GUIDE_LATTER
                            )
                        }
                    )
                }
                Spacer(height = 29.dp)
                BoxGuideContent(
                    modifier = Modifier
                        .heightIn(
                            min = 0.dp,
                            max = 146.dp
                        )
                        .aspectRatio(16f / 9f)
                        .fillMaxWidth(),
                    inclination = 0f,
                    placeholder = {
                        BoxPlaceholder(
                            icon = R.drawable.music_note,
                            title = Strings.BOX_GUIDE_MUSIC
                        )
                    },
                )
                Spacer(1f)
                BottomNavButton(
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(height = 49.dp)
            }
        }
    }
}

@Composable
private fun BottomNavButton(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp),
    ) {
        BottomButton(
            modifier = Modifier
                .height(50.dp)
                .weight(1f),
            onClick = {

            },
        ) {
            Text(
                text = Strings.BOX_GUIDE_CHANGE_BOX,
                style = PackyTheme.typography.body04,
                color = PackyTheme.color.white
            )
        }
        Spacer(width = 16.dp)
        BottomButton(
            modifier = Modifier
                .height(50.dp)
                .weight(1f),
            onClick = {

            },
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "box guide add gift button",
                    tint = PackyTheme.color.white,
                )
                Spacer(width = 8.dp)
                Text(
                    text = Strings.BOX_GUIDE_GIFT,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.white
                )
            }
        }
    }
}

@Composable
private fun BottomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    Box(
        modifier = modifier
            .background(
                color = PackyTheme.color.black.copy(alpha = 0.3f),
                shape = RoundedCornerShape(100.dp)
            )
            .clickableWithoutRipple(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
private fun BoxPlaceholder(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "box guide placeholder icon",
            tint = PackyTheme.color.white,
        )
        Spacer(height = 12.dp)
        Text(
            text = title,
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.white
        )
    }
}

@Composable
private fun BoxGuideContent(
    modifier: Modifier = Modifier,
    inclination: Float = 0f,
    placeholder: @Composable () -> Unit,
    content: (@Composable () -> Unit)? = null,
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(1.dp)
                .rotate(inclination)
                .drawBehind {
                    drawRoundRect(
                        color = Color.White.copy(alpha = 0.3f),
                        style = dottedStroke,
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
        )
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            if (content != null) {
                content()
            } else {
                placeholder()
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Sticker(
    modifier: Modifier = Modifier,
    stickerUri: String? = null,
    inclination: Float = 0f,
) {
    Box(modifier = modifier) {
        if (stickerUri != null) {
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(inclination),
                model = stickerUri,
                contentDescription = "sticker image",
                contentScale = ContentScale.Crop,
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.plus_square_dashed),
                contentDescription = "sticker placeholder icon",
                tint = PackyTheme.color.white,
            )
        }
    }
}

@Composable
private fun TopBar(
    title: String,
    onBackClick: emitMviIntent<BoxGuideIntent>,
    onSaveClick: emitMviIntent<BoxGuideIntent>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = PackyTheme.color.white,
                    shape = CircleShape
                )
                .clickableWithoutRipple {
                    onBackClick(BoxGuideIntent.OnBackClick)
                }
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "back guide screen"
            )
        }
        Spacer(width = 16.dp)
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = PackyTheme.typography.body02.copy(
                textAlign = TextAlign.Start
            ),
            color = PackyTheme.color.white
        )
        Spacer(width = 16.dp)
        Box(modifier = Modifier
            .width(60.dp)
            .height(40.dp)
            .background(
                color = PackyTheme.color.white,
                shape = RoundedCornerShape(80.dp)
            )
            .clickableWithoutRipple {
                onSaveClick(BoxGuideIntent.OnSaveClick)
            }
        )
        {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = COMPLETE,
                style = PackyTheme.typography.body02.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray600
            )
        }
    }
}

@Composable
private fun BottomSheetNav(
    bottomSheetRoute: BoxGuideBottomSheetRoute,
    closeBottomSheet: () -> Unit,
    showSnackbar: (String) -> Unit,
) {
    when (bottomSheetRoute) {
        BoxGuideBottomSheetRoute.ADD_GIFT -> Unit
        BoxGuideBottomSheetRoute.ADD_LATTER -> {
            CreateBoxLatterScreen(
                closeBottomSheet = closeBottomSheet,
                showSnackbar = showSnackbar
            )
        }

        BoxGuideBottomSheetRoute.ADD_MUSIC -> {
            CreateBoxNavHost(
                modifier = Modifier.background(PackyTheme.color.white),
                closeBottomSheet = closeBottomSheet
            )
        }

        BoxGuideBottomSheetRoute.ADD_PHOTO ->
            CreateBoxAddPhotoScreen(closeBottomSheet = closeBottomSheet)

        BoxGuideBottomSheetRoute.ADD_STICKER_1 -> TODO()
        BoxGuideBottomSheetRoute.ADD_ADD_STICKER_2 -> TODO()
    }
}