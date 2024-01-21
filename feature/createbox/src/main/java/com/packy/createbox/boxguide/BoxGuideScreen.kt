package com.packy.createbox.boxguide

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.theme.PackyTheme
import com.packy.createbox.createboax.addlatter.CreateBoxLatterScreen
import com.packy.createbox.createboax.addphoto.CreateBoxAddPhotoScreen
import com.packy.createbox.createboax.navigation.CreateBoxBottomSheetRoute
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val bottomSheetState = SheetState(skipHiddenState = false, skipPartiallyExpanded = false)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)
    val scope = rememberCoroutineScope()

    var bottomSheetRoute by remember { mutableStateOf(BoxGuideBottomSheetRoute.ADD_MUSIC) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContent = {
            BottomSheetNav(bottomSheetRoute, scope, scaffoldState, navController)
        }) { innerPadding ->
        BackHandler(enabled = scaffoldState.bottomSheetState.isVisible) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        }
        Box(
            modifier = modifier
                .background(
                    PackyTheme.color.purple500
                )
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        scope.launch {
                            bottomSheetRoute = BoxGuideBottomSheetRoute.ADD_MUSIC
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Text(text = "음악추가하기")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            scope.launch {
                                bottomSheetRoute = BoxGuideBottomSheetRoute.ADD_LATTER
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                ) {
                    Text(text = "편지쓰기")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            scope.launch {
                                bottomSheetRoute = BoxGuideBottomSheetRoute.ADD_PHOTO
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                ) {
                    Text(text = "추억 사진 담기")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = "선물 추가하기")
                }
            }
            if (scaffoldState.bottomSheetState.isVisible) {
                Box(
                    modifier = Modifier
                        .clickableWithoutRipple {}
                        .fillMaxSize()
                        .background(
                            PackyTheme.color.black.copy(alpha = 0.6f)
                        )
                )
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetNav(
    bottomSheetRoute: BoxGuideBottomSheetRoute,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    navController: NavController
) {
    val closeBottomSheet: () -> Unit = {
        scope.launch {
            scaffoldState.bottomSheetState.hide()
        }
    }
    when (bottomSheetRoute) {
        BoxGuideBottomSheetRoute.ADD_GIFT -> Unit
        BoxGuideBottomSheetRoute.ADD_LATTER -> {
            CreateBoxLatterScreen(closeBottomSheet = closeBottomSheet)
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
