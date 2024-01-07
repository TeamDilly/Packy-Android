package config

import org.gradle.api.Project

internal fun Project.configureAndroidApplication() {
    with(pluginManager) {
        apply("com.android.application")
        apply("kotlin-android")
        apply("org.jetbrains.kotlin.android")
    }

    configureKotlinAndroid()
}