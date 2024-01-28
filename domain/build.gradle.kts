plugins {
    id("java-library")
    id("kotlin.library")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(project(":lib"))
    implementation(libs.paging.common)
}