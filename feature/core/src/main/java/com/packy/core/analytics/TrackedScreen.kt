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
    loggerEvents: Array<AnalyticsEvent>
) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                FirebaseAnalyticsWrapper.logEvent(
                    label,
                    loggerEvents.toBundle()
                )
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}

fun Array<AnalyticsEvent>.toBundle(): Bundle {
    val bundle = Bundle()
    for (loggerEvent in this) {
        bundle.putString(
            loggerEvent.key,
            loggerEvent.event
        )
    }
    return bundle
}