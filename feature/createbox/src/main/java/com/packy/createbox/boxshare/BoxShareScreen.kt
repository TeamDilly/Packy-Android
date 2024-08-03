package com.packy.createbox.boxshare

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.packy.core.analytics.AnalyticsConstant
import com.packy.core.analytics.TrackedScreen
import com.packy.core.common.Spacer
import com.packy.core.common.clickableWithoutRipple
import com.packy.core.designsystem.button.PackyButton
import com.packy.core.designsystem.button.buttonStyle
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.dialog.PackyDialogInfo
import com.packy.core.designsystem.progress.PackyProgressDialog
import com.packy.core.designsystem.topbar.PackyTopBar
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.feature.core.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BoxShareScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    moveToHomeClear: () -> Unit,
    viewModel: BoxShareViewModel = hiltViewModel()
) {
    TrackedScreen(
        label = AnalyticsConstant.AnalyticsLabel.VIEW,
        loggerEvents = arrayOf(AnalyticsConstant.PageName.BOX_SHARE)
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val shared by remember {
        derivedStateOf { uiState.shared }
    }
    val kakaoShare = KakaoShare()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val loading by remember { derivedStateOf { uiState.isLoading } }
    if (loading) {
        PackyProgressDialog()
    }

    var lazyShareDialog by remember {
        mutableStateOf<PackyDialogInfo?>(null)
    }
    if(lazyShareDialog != null){
        PackyDialog(packyDialogInfo = lazyShareDialog!!)
    }

    LaunchedEffect(
        context,
        viewModel
    ) {
        scope.launch {
            viewModel.initState()
            viewModel.effect.collect { effect ->
                when (effect) {
                    is BoxShareEffect.KakaoShare -> {
                        kakaoShare.sendGiftBox(
                            context = context,
                            kakaoCustomFeed = effect.kakaoCustomFeed,
                            sharedCallBack = {
                                viewModel.kakaoShare(it)
                            }
                        )
                    }

                    BoxShareEffect.MoveToBack -> {
                        navController.popBackStack()
                    }

                    BoxShareEffect.MoveToMain -> {
                        moveToHomeClear()
                    }

                    BoxShareEffect.ShowLazyShareDialog ->{
                        lazyShareDialog = PackyDialogInfo(
                            title = Strings.CREATE_BOX_LAZY_SHARE_DIALOG_TITLE,
                            subTitle = Strings.CREATE_BOX_LAZY_SHARE_DIALOG_DESCRIPTION,
                            dismiss = Strings.EXIT,
                            confirm = Strings.CANCEL,
                            onConfirm = {
                                lazyShareDialog = null
                            },
                            onDismiss = {
                                viewModel.emitIntent(BoxShareIntent.LazyShared)
                                lazyShareDialog = null
                            }
                        )
                    }
                }
            }
        }
    }

    BackHandler {
        if (shared == true) {
            viewModel.emitIntent(BoxShareIntent.OnExitClick)
        } else {
            viewModel.emitIntent(BoxShareIntent.OnLazySharClick)
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            PackyTopBar.Builder()
                .apply {
                    if (shared == true) {
                        endTextButton(
                            text = Strings.EXIT
                        ) {
                            viewModel.emitIntent(BoxShareIntent.OnExitClick)
                        }
                    } else {
                        endIconButton(
                            icon = R.drawable.cancle
                        ) {
                            viewModel.emitIntent(BoxShareIntent.OnLazySharClick)
                        }
                    }
                }
                .build()
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(48.dp)
            Text(
                text = uiState.receiverName + Strings.CREATE_BOX_ADD_SHARE_TITLE,
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
            Spacer(height = 12.dp)
            if(shared == false){
                Text(
                    modifier = Modifier.clickableWithoutRipple {
                        viewModel.emitIntent(BoxShareIntent.OnLazySharClick)
                    },
                    text = Strings.CREATE_BOX_ADD_SHARE_LAZY,
                    style = PackyTheme.typography.body02.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = PackyTheme.color.gray900
                )
            }
            Spacer(height = 24.dp)
        }
    }
}