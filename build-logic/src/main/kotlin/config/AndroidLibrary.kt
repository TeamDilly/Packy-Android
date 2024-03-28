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
        val bom = libs.findLibrary("firebase.bom").get()
        add("implementation", platform(bom))
        add("implementation" , libs.findLibrary("firebase.analytics").get())
    }

}