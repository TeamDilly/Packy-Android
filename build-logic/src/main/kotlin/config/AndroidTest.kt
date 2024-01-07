package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidTest() {
    val libs = extensions.libs
    dependencies {
        add("androidTestImplementation", libs.findLibrary("test.android.junit").get())
        add("androidTestImplementation", libs.findLibrary("test.android.espresso").get())
    }
}