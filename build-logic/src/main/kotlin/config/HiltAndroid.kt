package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltAndroid() {
    with(pluginManager) {
        apply("dagger.hilt.android.plugin")
        apply("org.jetbrains.kotlin.kapt")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("hilt.android").get())
        add("kapt", libs.findLibrary("hilt.android.compiler").get())
    }
}
