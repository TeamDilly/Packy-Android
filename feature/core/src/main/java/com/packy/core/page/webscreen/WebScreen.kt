package com.packy.core.page.webscreen

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
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
import com.packy.feature.core.BuildConfig
import com.packy.feature.core.R

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(
    navController: NavController,
    url: String,
    modifier: Modifier = Modifier
) {
    WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    var loadingProgress by remember {
        mutableStateOf(false)
    }
    if (loadingProgress) {
        PackyProgressDialog()
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
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
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webChromeClient = ChromeClient()
                        webViewClient = object : WebViewClient() {
                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                println("LOGEE onReceivedError $error")
                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?
                            ) {
                                super.onReceivedHttpError(view, request, errorResponse)
                                println("LOGEE onReceivedHttpError $errorResponse")
                            }

                            override fun onReceivedSslError(
                                view: WebView?,
                                handler: SslErrorHandler?,
                                error: SslError?
                            ) {
                                super.onReceivedSslError(view, handler, error)
                                println("LOGEE onReceivedSslError $error")
                            }

                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                println("LOGEE shouldOverrideUrlLoading")
                                loadingProgress = true
                                return false
                            }

                            override fun onPageFinished(
                                view: WebView?,
                                url: String?
                            ) {
                                println("LOGEE onPageFinished")
                                super.onPageFinished(view, url)
                                loadingProgress = false
                            }
                        }
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            cacheMode = WebSettings.LOAD_DEFAULT
                            blockNetworkLoads = false
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            setGeolocationEnabled(true)
                            setSupportZoom(true)
                            builtInZoomControls = true
                            displayZoomControls = false
                        }
                    }
                },
                update = { webView ->
                    println("LOGEE $url")
                    webView.loadUrl(url)
                }
            )
        }
    }
}

class ChromeClient : WebChromeClient() {

}