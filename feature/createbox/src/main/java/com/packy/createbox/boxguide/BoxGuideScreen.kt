package com.packy.createbox.boxguide

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.packy.createbox.createboax.navigation.CreateBoxNavHost
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoxGuideScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    Scaffold { innerPadding ->
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(true) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = modifier.padding(innerPadding)
        ) {

        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState,
                dragHandle = null
            ) {
                CreateBoxNavHost {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }
            }
        }
    }

}
