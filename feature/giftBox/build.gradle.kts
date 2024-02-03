plugins {
    id("android.library.feature")
}

android {
    namespace = "com.packy.feature.main"
}

dependencies {
    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":library:mvi"))
    implementation(project(":feature:core"))
    implementation(project(":common-android"))
    implementation(project(":lib"))
}

