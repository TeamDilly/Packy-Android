plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.data"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(project(":common"))

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization.converter)
}