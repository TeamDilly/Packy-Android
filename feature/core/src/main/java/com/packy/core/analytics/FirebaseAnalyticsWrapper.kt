package com.packy.core.analytics

import android.os.Bundle
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseAnalyticsWrapper {

    fun setUserProperty(name: String, value: String?) {
        Firebase.analytics.setUserProperty(name, value)
    }

    fun setUserId(userId: String) {
        Firebase.analytics.setUserId(userId)
    }

    fun logEvent(label: AnalyticsConstant.AnalyticsLabel, bundle: Bundle?) {
        Firebase.analytics.logEvent(label.label, bundle)
    }

}