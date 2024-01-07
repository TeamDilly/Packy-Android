package config

import androidExtension
import libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose() {
    val libs = extensions.libs

    androidExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }
    }
    dependencies {
        val bom = libs.findLibrary("compose.bom").get()
        add("implementation", platform(bom))

        add("implementation", libs.findBundle("compose").get())
    }
}