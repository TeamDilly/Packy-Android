package com.packy.createbox.boxguide

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.snackbar.PackySnackBarHost
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings.COMPLETE
import com.packy.createbox.createboax.addlatter.CreateBoxLatterScreen
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoScreen
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import com.packy.feature.core.R
import com.packy.mvi.ext.emitMviIntent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    closeCreateBox: () -> Unit,
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
                .background(PackyTheme.color.gray900)
        ) {
            Spacer(height = 8.dp)
            TopBar(
                title = "To.이호이호이호",
                onBackClick = viewModel::emitIntentThrottle,
                onSaveClick = viewModel::emitIntentThrottle,
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
    }
}