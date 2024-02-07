package com.packy.createbox.boxshare

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.packy.common.kakaoshare.KakaoShare
import com.packy.core.common.Spacer
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.lib.utils.Resource
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BoxShareViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val kakaoShare = KakaoShare()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    DisposableEffect(
        context,
        viewModel
    ) {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
            }
        }
        scope.launch {
            viewModel.initState()
            viewModel.effect.collect { effect ->
                when (effect) {
                    BoxShareEffect.FailedShare -> println("LOGEE FailedShare")
                    BoxShareEffect.SuccessShare -> println("LOGEE SuccessShare")
                    is BoxShareEffect.KakaoShare -> {
                        kakaoShare.sendGiftBox(
                            context = context,
                            kakaoCustomFeed = effect.kakaoCustomFeed,
                            sharedCallBack = {
                                if (it is Resource.Success) {
                                    val filter = IntentFilter(it.data)
                                    context.registerReceiver(
                                        broadcastReceiver,
                                        filter
                                    )
                                } else {
                                    viewModel.kakaoShare(it)
                                }
                            }
                        )
                    }
                }
            }
        }
        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(1f)
            Text(
                text = Strings.CREATE_BOX_ADD_SHARE_TITLE,
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900
            )
            Spacer(height = 20.dp)
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
                text = uiState.boxTitle ?: "",
                style = PackyTheme.typography.body04.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900,
            )
            Spacer(height = 64.dp)
            GlideImage(
                modifier = Modifier.size(240.dp),
                model = uiState.boxImageUrl,
                contentDescription = "BoxImage"
            )
            Spacer(1f)
            PackyButton(
                modifier = Modifier.padding(horizontal = 24.dp),
                text = Strings.CREATE_BOX_ADD_SHARE_SEND_KAKAO,
                style = buttonStyle.large.black,
                onClick = {
                    viewModel.emitIntentThrottle(BoxShareIntent.ShareKakao)
                }
            )
            Spacer(height = 62.dp)
        }
    }
}