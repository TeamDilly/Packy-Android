plugins {
    id("android.application.core")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.packy"

    defaultConfig {
        targetSdk = 34
        versionCode = 7
        versionName = "1.4.0"
        applicationId = "com.packy"

        multiDexEnabled = true
    }

    signingConfigs {
        maybeCreate("release").apply {
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
    buildTypes {
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
    implementation(project(":feature:createbox"))
    implementation(project(":feature:giftBox"))
    implementation(project(":feature:home"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:core"))

    implementation(project(":library:pref"))
    implementation(project(":library:account"))

    implementation(libs.kakao.user)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.branch.io)
}