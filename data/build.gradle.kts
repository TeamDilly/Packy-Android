plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":library:pref"))
    implementation(project(":lib"))
}