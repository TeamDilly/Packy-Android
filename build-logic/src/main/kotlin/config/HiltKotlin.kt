package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureHiltKotlin() {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.kapt")
    }

    val libs = extensions.libs
    dependencies {
        add("implementation", libs.findLibrary("hilt.core").get())
        add("kapt", libs.findLibrary("hilt.compiler").get())
    }
}
