package com.packy.core.page.webscreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.progress.PackyProgress
import com.packy.core.designsystem.progress.PackyProgressDialog
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
    println("LOGEE url : $url")
    var loadingProgress by remember {
        mutableStateOf(false)
    }
    if (loadingProgress) {
        PackyProgressDialog()
    }

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
        Box(
            modifier = modifier
                .padding(innerPadding),
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                loadingProgress = true
                                return false
                            }

                            override fun onPageFinished(
                                view: WebView?,
                                url: String?
                            ) {
                                super.onPageFinished(
                                    view,
                                    url
                                )
                                loadingProgress = false
                            }
                        }
                        settings.javaScriptEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.domStorageEnabled = true

                    }
                },
                update = { webView ->
                    webView.loadUrl(url)
                }
            )
        }
    }
}