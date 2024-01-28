plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(project(":lib"))
    implementation(libs.bundles.ktor.android)
    implementation(libs.bundles.paging)
}