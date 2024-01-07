package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configurationKotlinSerialization() {
    val libs = extensions.libs

    with(pluginManager) {
        apply("kotlinx-serialization")
    }

    dependencies {
        add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
    }
}