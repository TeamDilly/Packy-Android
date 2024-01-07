package config

import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinTest() {
    val libs = extensions.libs
    dependencies {
        add("testImplementation", libs.findLibrary("test.junit").get())
    }
}