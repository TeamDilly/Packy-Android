package com.packy.core.analytics

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun TrackedScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    label: AnalyticsConstant.AnalyticsLabel,
    vararg loggerEvents: AnalyticsEvent
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                val bundle = Bundle()
                for (loggerEvent in loggerEvents) {
                    bundle.putString(
                        loggerEvent.key,
                        loggerEvent.event
                    )
                }
                FirebaseAnalyticsWrapper.logEvent(
                    label.label,
                    bundle
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}