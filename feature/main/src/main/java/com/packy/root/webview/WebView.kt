package com.packy.root.webview

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.feature.core.R

@Composable
fun ComposeWebViewScreen(
    navController: NavController,
    url: String
) {
    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .startIconButton(icon = R.drawable.cancle) {
                    navController.popBackStack()
                }
                .build()
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier.padding(innerPadding),
            factory = {
                WebView(it).apply {
                    webViewClient = WebViewClient()
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }, update = {
                it.loadUrl(url)
            })
    }
}