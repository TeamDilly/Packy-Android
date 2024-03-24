package com.packy.root

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.giftbox.navigation.GiftBoxRoute
import com.example.home.root.HomeRoute.HOME_ROOT
import com.packy.core.designsystem.dialog.PackyDialog
import com.packy.core.designsystem.dialog.PackyDialogInfo
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.di.BuildConfig
import com.packy.feature.core.R
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.deeplink.DeepLinkController
import com.packy.root.navigation.MainRoute
import kotlinx.coroutines.delay


@Composable
fun LaunchScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    deepLinkController: DeepLinkController = DeepLinkController.NonDeepLink,
    viewModel: RootComposeViewModel = hiltViewModel()
) {

    var packyDialog by rememberSaveable {
        mutableStateOf<PackyDialogInfo?>(null)
    }

    if (packyDialog != null) {
        PackyDialog(packyDialogInfo = packyDialog!!)
    }

    val context = LocalContext.current

    fun openAppStore() {
        val appPackageName = context.packageName
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("market://details?id=$appPackageName")
        )
        context.startActivity(
            intent
        )
    }


    LaunchedEffect(null) {

        viewModel.checkUserStatusOnAppEntry()
            .collect {
                when (it) {
                    UserState.NOT_REGISTERED,
                    UserState.WITHDRAWAL,
                    UserState.BLACKLIST,
                    UserState.INVALID_STATUS -> {
                        navController.navigate(OnboardingRoute.ONBOARDING_NAV_GRAPH) {
                            popUpTo(
                                MainRoute.LAUNCH_ROUTE
                            ) {
                                inclusive = true
                            }
                        }
                    }

                    UserState.REGISTERED -> {
                        delay(1500)
                        when (deepLinkController) {
                            DeepLinkController.NonDeepLink -> navController.navigate(HOME_ROOT) {
                                popUpTo(
                                    MainRoute.LAUNCH_ROUTE
                                ) {
                                    inclusive = true
                                }
                            }

                            is DeepLinkController.OpenBox -> {
                                navController.navigate(
                                    GiftBoxRoute.getGiftBoxRootRoute(
                                        deepLinkController.boxId.toLong(),
                                        skipArr = false,
                                        shouldShowShared = false
                                    )
                                ) {
                                    popUpTo(
                                        MainRoute.LAUNCH_ROUTE
                                    ) {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    }

                    UserState.NEED_UPDATE -> {
                        packyDialog = PackyDialogInfo(
                            title = Strings.NEED_UPDATE_TITLE,
                            subTitle = Strings.NEED_UPDATE_DESCRIPTION,
                            confirm = Strings.NEED_UPDATE_BUTTON,
                            onConfirm = {
                                packyDialog = null
                                openAppStore()
                            },
                            backHandler = {
                                openAppStore()
                            }
                        )
                    }

                    UserState.LOADING -> Unit
                }
            }
    }

    Scaffold { innerPadding ->
        val innerPadding = innerPadding
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = PackyTheme.color.purple500)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.launch_logo),
                contentDescription = "LOGO",
                tint = PackyTheme.color.white
            )
        }
    }
}