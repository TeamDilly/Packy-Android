package config

import androidExtension
import applicationExtension
import libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureKotlinAndroid() {
    val libs = extensions.libs

    androidExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 28

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildTypes {
            getByName("debug"){
                isMinifyEnabled = false
                defaultConfig {
                    buildConfigField(
                        "String",
                        "VERSION_NAME",
                        "\"1.5.0\""
                    )
                }
                buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"https://dev.packyforyou.shop/\""
                )
            }
            getByName("release") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
                defaultConfig {
                    buildConfigField(
                        "String",
                        "VERSION_NAME",
                        "\"1.5.0\""
                    )
                }
                buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"https://prod.packyforyou.shop/\""
                )
            }
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    tasks.withType(KotlinCompile::class.java).configureEach {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }

    dependencies{
        add("implementation", libs.findLibrary("android.core").get())
        add("implementation", libs.findLibrary("android.appcompat").get())
        add("implementation", libs.findLibrary("android.material").get())
    }
}