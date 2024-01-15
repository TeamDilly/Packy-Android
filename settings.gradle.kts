pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Packy"

include(":app")
include(":domain")
include(":feature")
include(":feature:main")
include(":data")
include(":library")
include(":library:mvi")
include(":library:account")
include(":library:pref")
include(":di")
include(":feature:core")
include(":feature:onboarding")
include(":common-android")
include(":lib")
