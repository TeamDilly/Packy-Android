plugins {
    id("android.library.feature")
}

android {
    namespace = "com.packy.feature.core"
}

dependencies{
    implementation(project(":domain"))
    implementation(libs.youtube.player)
    implementation(libs.permissions)
}