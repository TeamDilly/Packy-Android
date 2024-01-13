plugins {
    id("android.library.feature")
}

android {
    namespace = "com.packy.feature.core"
}

dependencies{
    implementation(libs.youtube.player)
}