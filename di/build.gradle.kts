plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.di"
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            defaultConfig {
                buildConfigField(
                    "String",
                    "VERSION_NAME",
                    "\"1.0.0\""
                )
            }
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://dev.packyforyou.shop/\""
            )
        }
    }
    packaging {
        resources {
            excludes.add("META-INF/INDEX.LIST")
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(project(":common-android"))
    implementation(project(":lib"))

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.ktor.android)
}