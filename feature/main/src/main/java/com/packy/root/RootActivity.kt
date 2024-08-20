package com.packy.root

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.packy.core.theme.PackyTheme
import com.packy.data.local.GlobalPrefManager
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    @Inject
    lateinit var globalPrefManager : GlobalPrefManager

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        Branch.sessionBuilder(this).withCallback { branchUniversalObject, linkProperties, error ->
            if (error == null) {
                // 딥링크 데이터 처리
                val deepLinkData = branchUniversalObject?.contentMetadata?.customMetadata
                val boxId = deepLinkData?.get("boxId")

                globalPrefManager.apply {
                    GlobalScope.launch {
                        deferredLinkBoxId.putData(boxId?.toLongOrNull())
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