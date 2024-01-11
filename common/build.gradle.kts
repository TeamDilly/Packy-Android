plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.common"
}

dependencies {
    implementation(project(":library:pref"))
}