package com.packy.root

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.packy.core.theme.PackyTheme
import com.packy.data.local.GlobalPrefManager
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Branch.sessionBuilder(this).withCallback { branchUniversalObject, linkProperties, error ->
            if (error == null) {
                // 딥링크 데이터 처리
                val deepLinkData = branchUniversalObject?.contentMetadata?.customMetadata
                val boxId = deepLinkData?.get("boxId")

                GlobalPrefManager(context = this).apply {
                    GlobalScope.launch {
                        deferredLinkBoxId.putData(boxId)
                    }
                }

            } else {
                Log.e("BranchSDK", "Branch 초기화 오류: ${error.message}")
            }
        }.withData(this.intent.data).init()

        setContent {
            PackyTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = PackyTheme.color.white
                ) {
                    RootCompose(
                        navController = navController
                    )
                }
            }
        }
    }
}