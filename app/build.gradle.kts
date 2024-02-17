plugins {
    id("android.application.core")
}

android {
    namespace = "com.packy"

    defaultConfig{
        targetSdk = 33
        versionCode = 3
        versionName = "1.0.1"
        applicationId = "com.packy"
    }

    buildFeatures {
        buildConfig = true
    }
    signingConfigs {
        maybeCreate("release").apply{
            val KEY_ALIAS: String by project
            val KEY_PASSWORD: String by project
            val STORE_FILE: String by project
            val STORE_PASSWORD: String by project
            keyAlias = KEY_ALIAS
            storeFile = file(STORE_FILE)
            keyPassword = KEY_PASSWORD
            storePassword = STORE_PASSWORD
        }
    }
    buildTypes{
        maybeCreate("debug").apply {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        maybeCreate("release").apply {
            signingConfig = signingConfigs.getByName("release")
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