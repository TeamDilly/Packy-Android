package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidLibrary() {
    val libs = extensions.libs
    with(pluginManager) {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
    }

    configureKotlinAndroid()

    dependencies {
        add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
        add("implementation" , libs.findLibrary("okhttp").get())
        add("implementation", libs.findLibrary("retrofit").get())
        add("implementation", libs.findLibrary("retrofit.kotlin.serialization.converter").get())
    }

}