plugins {
    id("android.application.core")
}

android {
    namespace = "com.packy"

    buildFeatures {
        buildConfig = true
    }

    buildTypes{
        debug {
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
}