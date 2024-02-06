package com.packy.core.page.webscreen

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.feature.core.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(
    navController: NavController,
    url: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            PackyTopBar.Builder()
                .endIconButton(icon = R.drawable.cancle) {
                    navController.popBackStack()
                }
                .build()
        },
        containerColor = PackyTheme.color.white
    ) { innerPadding ->
        AndroidView(
            modifier = modifier
                .padding(innerPadding),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()

                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )
    }
}