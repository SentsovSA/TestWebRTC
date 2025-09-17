enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://androidx.dev/snapshots/builds/11882262/artifacts/repository")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://jitpack.io")
        //maven("https://plugins.gradle.org/m2/")
        maven("https://androidx.dev/snapshots/builds/11882262/artifacts/repository")
    }
}

rootProject.name = "TestWebRTC"

include(":app")
include(":app-api")
include(":app-core")
include(":app-data")
include(":app-database")
include(":app-domain")
include(":app-viewmodels")
include(":app-webview")
include(":app-filepicker")
include(":app-camera")
include(":app-context")

//include(":scanner")