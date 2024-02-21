package com.example.home.mybox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.filter
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.home.mybox.widget.LazyBoxItem
import com.packy.common.authenticator.ext.toFormatTimeStampString
import com.packy.core.common.NoRippleTheme
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.dialog.PackyDialogInfo
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.values.Strings.MY_BOX_TITLE
import com.packy.domain.model.home.HomeBox
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MyBoxScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    viewModel: MyBoxViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyBox by remember {
        derivedStateOf { uiState.lazyBox }
    }
    val showType by remember {
        derivedStateOf { uiState.showTab }
    }
    val sendBox: LazyPagingItems<HomeBox> =
        viewModel.uiState.map { it.sendBox.filter { box -> !uiState.removeItemBox.contains(box.boxId) } }
            .collectAsLazyPagingItems()
    val receiveBox: LazyPagingItems<HomeBox> =
        viewModel.uiState.map { it.receiveBox.filter { box -> !uiState.removeItemBox.contains(box.boxId) } }
            .collectAsLazyPagingItems()

    val lazyBoxState = rememberPagerState(pageCount = { lazyBox.size })
    val pagerState = rememberPagerState(pageCount = { 2 })

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf<Pair<Long, Boolean>?>(null) }
    var showDeleteDialog by remember { mutableStateOf<PackyDialogInfo?>(null) }

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }

    val sendBoxState: LazyGridState = rememberLazyGridState()
    val receiverBoxState: LazyGridState = rememberLazyGridState()

    val visibleLazyBox = @Composable {
        val firstVisibleItemIndex = if (pagerState.currentPage == 0) {
            remember { derivedStateOf { lazyBoxScrollVisible(sendBoxState) } }
        } else {
            remember { derivedStateOf { lazyBoxScrollVisible(receiverBoxState) } }
        }
        firstVisibleItemIndex.value && lazyBox.isNotEmpty()
    }

    LaunchedEffect(Unit) {
        viewModel.getReceiveBoxes()
        viewModel.getSendBoxes()
        viewModel.getLazyBoxes()
        viewModel.effect.collect { effect ->
            when (effect) {
                is MyBoxEffect.MoveToBack -> navController.popBackStack()
                is MyBoxEffect.MoveToBoxDetail -> moveToBoxDetail(effect.boxId)
                is MyBoxEffect.ShowDeleteBottomSheet -> {
                    showBottomSheet = effect.boxId to effect.isLazyBox
                }

                is MyBoxEffect.ShowDeleteDialog -> {
                    showDeleteDialog = PackyDialogInfo(
                        title = Strings.DELETE_BOX_TITLE,
                        subTitle = Strings.DELETE_BOX_SUB_TIELE,
                        dismiss = Strings.DELETE,
                        confirm = Strings.CANCEL,
                        onConfirm = { showDeleteDialog = null },
                        onDismiss = {
                            viewModel.emitIntentThrottle(
                                MyBoxIntent.OnDeleteBoxClick(
                                    boxId = effect.boxId,
                                    isLazyBox = effect.isLazyBox
                                )
                            )
                            showDeleteDialog = null
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(true) {
        viewModel.uiState
            .map { it.showTab }
            .filter { it.ordinal != pagerState.pageCount }
            .distinctUntilChanged()
            .collect {
                pagerState.animateScrollToPage(it.ordinal)
            }
    }

    LaunchedEffect(true) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .filter { pagerState.pageCount != uiState.showTab.ordinal }
            .collect { page ->
            viewModel.emitIntent(MyBoxIntent.ChangeShowBoxType(MyBoxType.entries[page]))
        }
    }

    if (showDeleteDialog != null) {
        PackyDialog(showDeleteDialog!!)
    }

    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startTitle(MY_BOX_TITLE)
                .build()
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->

        if (showBottomSheet != null) {
            MyBoxDeleteBottomSheet(
                boxId = showBottomSheet!!.first,
                sheetState = sheetState,
                scope = scope,
                onDeleteClick = { boxId ->
                    val isLazyBox = showBottomSheet!!.second
                    viewModel.emitIntent(
                        MyBoxIntent.OnClickDeleteMyBoxBottomSheet(
                            boxId,
                            isLazyBox
                        )
                    )
                },
                closeSheet = { showBottomSheet = null }
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedVisibility(
                visible = visibleLazyBox(),
                enter = fadeIn(tween(500)) + expandVertically(tween(500)),
                exit = fadeOut(tween(500)) + shrinkVertically(tween(500)),
            ) {
                Column {
                    Spacer(32.dp)
                    Text(
                        text = Strings.LAZY_BOX_TITLE,
                        style = PackyTheme.typography.body01,
                        color = PackyTheme.color.gray900,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(12.dp)
                    HorizontalPager(
                        modifier = Modifier.fillMaxWidth(),
                        state = lazyBoxState,
                        contentPadding = PaddingValues(
                            start = 14.dp,
                            end = 40.dp
                        ),
                        pageSpacing = 16.dp
                    ) { page ->
                        LazyBoxItem(
                            lazyBox = lazyBox[page],
                            onClick = { boxId -> viewModel.emitIntent(MyBoxIntent.ClickMyBox(boxId)) },
                            onMoreClick = { boxId -> viewModel.emitIntent(MyBoxIntent.OnLayBoxMoreClick(boxId)) }
                        )
                    }
                    Spacer(24.dp)
                }
            }
            MyBoxTab(
                selectedTab = uiState.showTab,
                onClick = viewModel::emitIntent
            )
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState,
                beyondBoundsPageCount = 1
            ) {
                when (it) {
                    MyBoxType.SEND.ordinal -> MyBoxList(
                        modifier = Modifier.fillMaxSize(),
                        boxes = sendBox,
                        isDeletedBox = { boxId -> uiState.removeItemBox.contains(boxId) },
                        moveToCreateBox = moveToCreateBox,
                        moveToBoxDetail = moveToBoxDetail,
                        deleteMyBox = { boxId -> viewModel.emitIntent(MyBoxIntent.OnMyBoxMoreClick(boxId)) },
                        emptyText = Strings.HOME_MY_BOX_EMPTY_SEND_BOX,
                        nameTag = Strings.TO,
                        state = sendBoxState
                    )

                    MyBoxType.RECEIVE.ordinal -> MyBoxList(
                        modifier = Modifier.fillMaxSize(),
                        boxes = receiveBox,
                        isDeletedBox = { boxId -> uiState.removeItemBox.contains(boxId) },
                        moveToCreateBox = moveToCreateBox,
                        moveToBoxDetail = moveToBoxDetail,
                        deleteMyBox = { boxId -> viewModel.emitIntent(MyBoxIntent.OnMyBoxMoreClick(boxId)) },
                        emptyText = Strings.HOME_MY_BOX_EMPTY_RECEIVE_BOX,
                        nameTag = Strings.FROM,
                        showCreateBoxButton = false,
                        state = receiverBoxState
                    )
                }
            }
        }
    }
}

private fun lazyBoxScrollVisible(sendBoxState: LazyGridState) =
    sendBoxState.firstVisibleItemIndex == 0 && sendBoxState.firstVisibleItemScrollOffset in 0..150

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyBoxDeleteBottomSheet(
    boxId: Long,
    sheetState: SheetState,
    scope: CoroutineScope = rememberCoroutineScope(),
    onDeleteClick: (Long) -> Unit = {},
    closeSheet: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = closeSheet,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = PackyTheme.color.white,
    ) {
        Column {
            Spacer(height = 8.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickableWithoutRipple {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                onDeleteClick(boxId)
                                if (!sheetState.isVisible) {
                                    closeSheet()
                                }
                            }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(

                    text = Strings.DELETE,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900,
                    textAlign = TextAlign.Center
                )
            }

            Divider()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickableWithoutRipple {
                        scope
                            .launch { sheetState.hide() }
                            .invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    closeSheet()
                                }
                            }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = Strings.CANCEL,
                    style = PackyTheme.typography.body02,
                    color = PackyTheme.color.gray900,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(height = 16.dp)
        }
    }
}

@Composable
private fun MyBoxList(
    modifier: Modifier = Modifier,
    boxes: LazyPagingItems<HomeBox>,
    isDeletedBox: (Long) -> Boolean,
    moveToCreateBox: () -> Unit,
    moveToBoxDetail: (Long) -> Unit,
    deleteMyBox: (Long) -> Unit = {},
    emptyText: String,
    nameTag: String,
    showCreateBoxButton: Boolean = true,
    state: LazyGridState
) {
    if (boxes.itemCount == 0 && boxes.loadState.refresh !is LoadState.Loading) {
        EmptyMyBoxes(
            modifier = modifier,
            emptyText = emptyText,
            moveToCreateBox = moveToCreateBox,
            showCreateBoxButton = showCreateBoxButton
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier
                .background(color = PackyTheme.color.gray100),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = state,
            columns = GridCells.Fixed(2),
        ) {
            items(boxes.itemCount) { index ->
                val box = boxes[index] ?: return@items
                MyBoxItem(
                    modifier = Modifier
                        .fillMaxSize(),
                    moveToBoxDetail = moveToBoxDetail,
                    deleteMyBox = deleteMyBox,
                    box = box,
                    nameTag = nameTag
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MyBoxItem(
    modifier: Modifier = Modifier,
    moveToBoxDetail: (Long) -> Unit,
    deleteMyBox: (Long) -> Unit = {},
    box: HomeBox,
    nameTag: String
) {
    Column(
        modifier = modifier
            .background(
                color = PackyTheme.color.white,
                shape = RoundedCornerShape(8.dp)
            )
            .clickableWithoutRipple {
                moveToBoxDetail(box.boxId)
            },
    ) {
        Spacer(height = 16.dp)
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.CenterHorizontally),
            model = box.boxImageUrl,
            contentDescription = "Box Image",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Spacer(height = 12.dp)
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = nameTag + box.displayName,
            style = PackyTheme.typography.body06,
            color = PackyTheme.color.purple500,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(height = 4.dp)
        Text(
            modifier = Modifier
                .height(44.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            text = box.title,
            style = PackyTheme.typography.body03,
            color = PackyTheme.color.gray900,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = box.giftBoxDate.toFormatTimeStampString(),
                style = PackyTheme.typography.body06,
                color = PackyTheme.color.gray600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(1f)
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickableWithoutRipple {
                        deleteMyBox(box.boxId)
                    },
                imageVector = ImageVector.vectorResource(id = R.drawable.ellipsis),
                contentDescription = "Delete",
                tint = PackyTheme.color.gray600,
            )
        }
        Spacer(height = 16.dp)
    }
}

@Composable
private fun EmptyMyBoxes(
    modifier: Modifier,
    emptyText: String,
    moveToCreateBox: () -> Unit,
    showCreateBoxButton: Boolean = true
) {
    Column(
        modifier = modifier
            .background(color = PackyTheme.color.gray100),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = emptyText,
            style = PackyTheme.typography.heading02,
            color = PackyTheme.color.gray900
        )
        Spacer(height = 4.dp)
        Text(
            text = Strings.HOME_MY_BOX_EMPTY_NUDGE,
            style = PackyTheme.typography.body02,
            color = PackyTheme.color.gray600
        )
        if (showCreateBoxButton) {
            Spacer(height = 24.dp)
            Row(
                modifier = Modifier
                    .background(
                        color = PackyTheme.color.purple500,
                        shape = CircleShape
                    )
                    .padding(
                        horizontal = 24.dp,
                        vertical = 17.dp
                    )
                    .clickableWithoutRipple(onClick = moveToCreateBox),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = Strings.HOME_CREATE_BUTTON,
                    style = PackyTheme.typography.body04,
                    color = PackyTheme.color.white
                )
                Spacer(width = 8.dp)
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Add",
                    tint = PackyTheme.color.white
                )
            }
        }
    }
}

@Composable
private fun MyBoxTab(
    selectedTab: MyBoxType,
    onClick: emitMviIntent<MyBoxIntent>
) {
    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                color = PackyTheme.color.gray900,
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal])
            )
        },
    ) {
        MyBoxType.entries.forEach { tabType ->
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                val isSelected = selectedTab == tabType
                Tab(
                    text = {
                        Text(
                            text = tabType.title,
                            style = if (isSelected) PackyTheme.typography.body01 else PackyTheme.typography.body02,
                            color = if (isSelected) PackyTheme.color.gray900 else PackyTheme.color.gray600,
                        )
                    },
                    selected = isSelected,
                    onClick = { onClick(MyBoxIntent.ChangeShowBoxType(tabType)) },
                )
            }
        }
    }
}