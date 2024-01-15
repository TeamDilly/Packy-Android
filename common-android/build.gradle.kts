plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.common"
}

dependencies {
    implementation(project(":library:pref"))
    implementation(project(":lib"))
    implementation(libs.kakao.user)
}