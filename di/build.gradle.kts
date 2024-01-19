plugins {
    id("android.library.core")
}

android {
    namespace = "com.packy.di"
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"http://packy-dev.ap-northeast-2.elasticbeanstalk.com/\""
            )
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":library:pref"))
    implementation(project(":library:account"))
    implementation(project(":common-android"))
    implementation(project(":lib"))

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization.converter)
}