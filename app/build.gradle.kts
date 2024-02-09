plugins {
    id("android.application.core")
}

android {
    namespace = "com.packy"

    defaultConfig{
        versionCode = 1
        versionName = "1.0.0"
        applicationId = "com.packy"
    }

    buildFeatures {
        buildConfig = true
    }
    signingConfigs {
        maybeCreate("release").apply{
            storeFile = STORE_FILE
        }
    }
    buildTypes{
        maybeCreate("debug").apply {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        maybeCreate("release").apply {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":di"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":feature:main"))

    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(libs.kakao.user)
}