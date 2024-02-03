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
    implementation(project(":library:account"))
    implementation(project(":feature:core"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:createbox"))
    implementation(project(":feature:giftBox"))
}

