plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.di"
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