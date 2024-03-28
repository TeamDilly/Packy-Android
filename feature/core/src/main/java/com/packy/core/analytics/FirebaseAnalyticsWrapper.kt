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

    fun logEvent(eventTag: String, bundle: Bundle?) {
        Firebase.analytics.logEvent(eventTag, bundle)
    }

}