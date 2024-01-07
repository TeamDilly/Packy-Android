plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.library.pref"
}

dependencies {
    implementation(libs.androidx.data.store)
}