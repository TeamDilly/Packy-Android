package com.packy.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.packy.core.theme.PackyTheme
import com.packy.onboarding.navigation.OnboardingRoute
import com.packy.root.navigation.PackyNavHost

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PackyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PackyTheme.color.white
                ) {
                    PackyNavHost(
                        navController = rememberNavController(),
                        startDestination = OnboardingRoute.ONBOARDING_NAV_GRAPH
                    )
                }
            }
        }
    }
}