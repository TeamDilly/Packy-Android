package com.example.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.common.widget.DeleteBottomSheet
import com.example.home.common.widget.LazyBoxItem
import com.example.home.root.HomeRoute.MY_BOX
import com.packy.feature.core.R
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.dialog.PackyDialogInfo
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.screen.error.ErrorDialog
import com.packy.core.screen.error.ErrorDialogInfo
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.BoxShakeAnimation
import com.packy.domain.model.getbox.GiftBox
import com.packy.domain.model.home.BoxType
import com.packy.domain.model.home.HomeBox
import com.packy.domain.model.home.LazyBox
import com.packy.domain.model.home.NoticeGiftBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long, Boolean) -> Unit,
    moveToBoxOpenMotion: (GiftBox) -> Unit,
    moveSettings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val uiState by viewModel.uiState.collectAsState()
    val giftBoxes by remember {
        derivedStateOf { uiState.giftBoxes }
    }
    val lazyBoxes by remember {
        derivedStateOf { uiState.lazyBox }
    }

    val showNoticeGiftBoxBottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val noticeGiftBox by remember {
        derivedStateOf { uiState.noticeGiftBox }
    }

    var errorDialog by remember { mutableStateOf<ErrorDialogInfo?>(null) }
    if (errorDialog != null) {
        ErrorDialog(
            errorDialog!!
        )
    }

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf<Long?>(null) }
    var showDeleteDialog by remember { mutableStateOf<PackyDialogInfo?>(null) }
    if (showDeleteDialog != null) {
        PackyDialog(showDeleteDialog!!)
    }

    LaunchedEffect(Unit) {
        viewModel.getGiftBoxes()
        viewModel.resetPoint()
        viewModel.getNoticeGiftBox()
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.MoveToSetting -> {
                    moveSettings()
                }

                is HomeEffect.MoveToBoxDetail -> moveToBoxDetail(
                    effect.boxId,
                    effect.isLazyBox
                )

                HomeEffect.MoveToCreateBox -> moveToCreateBox()
                HomeEffect.MoveToMoreBox -> navController.navigate(MY_BOX)
                is HomeEffect.ThrowError -> {
                    errorDialog = ErrorDialogInfo(
                        message = effect.message,
                        retry = { errorDialog = null },
                        backHandler = { errorDialog = null }
                    )
                }

                is HomeEffect.ShowBottomSheetMore -> {
                    showBottomSheet = effect.boxId
                }

                is HomeEffect.ShowDeleteDialog -> {
                    showDeleteDialog = PackyDialogInfo(
                        title = Strings.DELETE_BOX_TITLE,
                        subTitle = Strings.DELETE_BOX_SUB_TIELE,
                        dismiss = Strings.DELETE,
                        confirm = Strings.CANCEL,
                        onConfirm = { showDeleteDialog = null },
                        onDismiss = {
                            viewModel.emitIntentThrottle(
                                HomeIntent.OnDeleteBoxClick(
                                    boxId = effect.boxId,
                                )
                            )
                            showDeleteDialog = null
                        }
                    )
                }

                is HomeEffect.MoveToBoxOpenMotion -> {
                    moveToBoxOpenMotion(effect.giftBox)
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .showLogo()
                .endIconButton(
                    icon = R.drawable.setting
                ) {
                    viewModel.emitIntentThrottle(HomeIntent.OnSettingClick)
                }
                .build()
        },
        contentWindowInsets = WindowInsets(0.dp),
        containerColor = PackyTheme.color.gray100
    ) { innerPadding ->

        if (showBottomSheet != null) {
            DeleteBottomSheet(
                boxId = showBottomSheet!!,
                sheetState = sheetState,
                scope = scope,
                onDeleteClick = { boxId ->
                    viewModel.emitIntent(
                        HomeIntent.OnClickDeleteMyBoxBottomSheet(
                            boxId,
                        )
                    )
                },
                closeSheet = { showBottomSheet = null }
            )
        }

        if (noticeGiftBox != null) {
            NoticeGiftBoxBottomSheet(
                noticeGiftBox = noticeGiftBox!!,
                sheetState = showNoticeGiftBoxBottomSheetState,
                scope = scope,
                hideBottomSheet = { viewModel.emitIntentThrottle(HomeIntent.HideBottomSheet) },
                openClick = { boxId ->
                    viewModel.emitIntentThrottle(
                        HomeIntent.OnBoxDetailClick(
                            giftBoxId = boxId,
                            showMotion = true
                        )
                    )
                }
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(height = 16.dp)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = PackyTheme.color.gray900,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickableWithoutRipple { viewModel.emitIntentThrottle(HomeIntent.OnCrateBoxClick) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 36.dp),
                    text = Strings.HOME_CREATE_BOX_TITLE,
                    style = PackyTheme.typography.body01,
                    color = PackyTheme.color.white,
                    textAlign = TextAlign.Center,
                )
                Image(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(154.dp),
                    painter = painterResource(id = com.example.home.R.drawable.home_create_box),
                    contentDescription = "create box",
                )
                Box(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .background(
                            color = PackyTheme.color.white,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 14.dp),
                        text = Strings.HOME_CREATE_BUTTON,
                        style = PackyTheme.typography.body04,
                        color = PackyTheme.color.gray900
                    )
                }
            }
            if (giftBoxes.isNotEmpty()) {
                Spacer(height = 16.dp)
                HomeGiftBox(
                    modifier = Modifier,
                    giftBoxes = giftBoxes,
                    onMoreClick = { viewModel.emitIntentThrottle(HomeIntent.OnMoreBoxClick) },
                    moveToBoxDetail = { viewModel.emitIntentThrottle(HomeIntent.OnBoxDetailClick(it)) }
                )
            }
            if (lazyBoxes.isNotEmpty()) {
                Spacer(height = 16.dp)
                LazyBoxes(
                    modifier = Modifier,
                    lazyBoxes = lazyBoxes,
                    onClick = { viewModel.emitIntentThrottle(HomeIntent.OnLazyBoxDetailClick(it)) },
                    onMoreClick = { viewModel.emitIntentThrottle(HomeIntent.OnBottomSheetMoreClick(it)) }
                )
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalGlideComposeApi::class
)
private fun NoticeGiftBoxBottomSheet(
    sheetState: SheetState,
    scope: CoroutineScope = rememberCoroutineScope(),
    hideBottomSheet: () -> Unit,
    noticeGiftBox: NoticeGiftBox,
    openClick: (Long) -> Unit = {}
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            scope
                .launch { sheetState.hide() }
                .invokeOnCompletion {
                    hideBottomSheet()
                }
        },
        dragHandle = null,
        containerColor = PackyTheme.color.white,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .height(48.dp)
                    .padding(
                        vertical = 4.dp,
                        horizontal = 16.dp
                    )
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.cancle),
                    contentDescription = "close",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickableWithoutRipple {
                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    hideBottomSheet()
                                }
                        }
                )
            }
            Text(
                text = Strings.GIFT_BOX_ARR_TITLE(noticeGiftBox.sender),
                style = PackyTheme.typography.heading01,
                color = PackyTheme.color.gray900,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(16.dp)
            Text(
                modifier = Modifier
                    .border(
                        1.dp,
                        PackyTheme.color.gray300,
                        RoundedCornerShape(40.dp)
                    )
                    .background(
                        PackyTheme.color.gray100,
                        RoundedCornerShape(40.dp)
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    ),
                text = noticeGiftBox.title,
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
            )
            Spacer(40.dp)
            BoxShakeAnimation(
                modifier = Modifier
                    .width(138.dp)
                    .height(160.dp)
                    .clickableWithoutRipple {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                openClick(noticeGiftBox.giftBoxId)
                                hideBottomSheet()
                            }
                    },
            ) {
                GlideImage(
                    model = noticeGiftBox.boxImage,
                    contentDescription = "gift box",
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(48.dp)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = Strings.OPEN,
                style = buttonStyle.large.black,
                onClick = {
                    scope
                        .launch { sheetState.hide() }
                        .invokeOnCompletion {
                            openClick(noticeGiftBox.giftBoxId)
                            hideBottomSheet()
                        }
                }
            )
            Spacer(16.dp)
        }
    }
}

@Composable
private fun LazyBoxes(
    modifier: Modifier,
    lazyBoxes: List<LazyBox>,
    onClick: (Long) -> Unit,
    onMoreClick: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = PackyTheme.color.white,
                shape = RoundedCornerShape(24.dp)
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier)
        Text(
            text = Strings.LAZY_BOX_TITLE,
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        lazyBoxes.forEach { lazyBox ->
            LazyBoxItem(
                lazyBox = lazyBox,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                onClick = onClick,
                onMoreClick = onMoreClick
            )
        }
        Box(modifier = Modifier)
    }
}

@Composable
private fun HomeGiftBox(
    modifier: Modifier = Modifier,
    giftBoxes: List<HomeBox>,
    onMoreClick: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = PackyTheme.color.white,
                shape = RoundedCornerShape(24.dp)
            ),
    ) {
        HomeGiftBoxes(
            giftBoxes = giftBoxes,
            onMoreClick = onMoreClick,
            moveToBoxDetail = moveToBoxDetail
        )
        Spacer(height = 24.dp)
    }
}

@Composable
private fun HomeGiftBoxes(
    modifier: Modifier = Modifier,
    giftBoxes: List<HomeBox>,
    onMoreClick: () -> Unit = {},
    moveToBoxDetail: (Long) -> Unit,
) {
    GiftBoxesTitle(
        modifier = modifier
            .padding(24.dp),
        onMoreClick = onMoreClick
    )
    LazyRow(
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(giftBoxes) { homeBox ->
            GiftBoxItem(
                modifier = Modifier
                    .width(120.dp),
                homeBox = homeBox,
                moveToBoxDetail = moveToBoxDetail
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun GiftBoxItem(
    modifier: Modifier = Modifier,
    homeBox: HomeBox,
    moveToBoxDetail: (Long) -> Unit,
) {
    Column(
        modifier = modifier
            .clickableWithoutRipple { moveToBoxDetail(homeBox.boxId) }
    ) {
        GlideImage(
            modifier = Modifier
                .height(138.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = homeBox.boxImageUrl,
            contentDescription = homeBox.title,
        )
        Spacer(height = 12.dp)
        val displayType = if (homeBox.type == BoxType.SENT) Strings.TO else Strings.FROM
        Text(
            text = displayType + homeBox.displayName,
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.purple500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(height = 4.dp)
        Text(
            text = homeBox.title,
            style = PackyTheme.typography.body03,
            color = PackyTheme.color.gray900,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun GiftBoxesTitle(
    modifier: Modifier = Modifier,
    onMoreClick: () -> Unit = {}
) {
    Row(
        modifier
    ) {
        Text(
            text = Strings.HOME_GIFT_BOXES,
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900,
        )
        Spacer(1f)
        Text(
            modifier = Modifier.clickableWithoutRipple { onMoreClick() },
            text = Strings.MORE,
            style = PackyTheme.typography.body04,
            color = PackyTheme.color.gray600,
        )
    }
}