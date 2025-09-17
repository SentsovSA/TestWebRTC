import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.org.jetbrains.kotlin.serialization)
    alias(libs.plugins.com.android.library)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        apiVersion.set(KotlinVersion.KOTLIN_2_0)
        jvmToolchain(17)
    }
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    cocoapods {
        ios.deploymentTarget = "15.0"
        framework {
            baseName = "core"
            isStatic = false
        }
        version = "1.0"
        /*pod("FirebaseCore") {
            version = "10.29.0"
        }
        pod("FirebaseCrashlytics") {
            version = "10.29.0"
            extraOpts += listOf("-compiler-option", "-fmodules")
        }
        pod("FirebaseMessaging") {
            version = "10.29.0"
        }

        tasks.named("podInstallSyntheticIos").configure {
            dependsOn("generateBuildConfig")
        }*/
    }
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
                optIn("kotlinx.datetime.format.FormatStringsInDatetimeFormats")
            }
        }
        commonMain {
            kotlin.srcDir("$buildDir/generated/kotlin")
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.platform.buffer)
                implementation(libs.kotlinx.io.core)
                implementation(libs.napier)
                implementation(libs.koin.core)
                implementation(project(":app-data"))
                api(libs.kmpnotifier)
                implementation(project(":app-domain"))
            }
        }
        androidMain {
            dependencies {
                implementation(libs.koin.android)
                implementation(project.dependencies.platform(libs.firebase.bom))
                implementation(libs.androidx.biometry)
                implementation(libs.firebase.analytics)
                implementation(libs.firebase.crashlytics)
                implementation(libs.firebase.messaging)
                implementation(libs.firebase.messaging.directboot)
            }
        }
        iosMain {
            dependencies {
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "test.webrtc.test.core"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    sourceSets {
        getByName("main") {
            manifest {
                srcFile("src/androidMain/AndroidManifest.xml")
            }
            res {
                srcDirs("src/androidMain/res")
            }
        }
    }
    buildTypes {
        /*release {
            buildConfigField("String", "BASE_URL", "\"https://jku.ost.group/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://main-service-test.test.ost.group/\"")
        }*/
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
dependencies {
    implementation(libs.androidx.core.i18n)
}

val debugBaseUrl = "https://main-service-test.test.ost.group"
val releaseBaseUrl = "https://jku.ost.group"

val isReleaseBuild: Boolean by lazy {
    gradle.startParameter.taskNames.any { taskName ->
        taskName.contains("Release", ignoreCase = true)
    } || System.getenv("CONFIGURATION")?.contains("Release", ignoreCase = true) == true
}

tasks.register("generateBuildConfig") {
    val outputDir = File("$buildDir/generated/kotlin")
    val outputFile = File(outputDir, "BuildConfig.kt")
    outputs.dir(outputDir)

    inputs.property("isReleaseBuild", isReleaseBuild)

    outputs.file(outputFile)

    doLast {
        outputDir.mkdirs()
        File(outputDir, "BuildConfig.kt").writeText(
            """
            object BuildConfig {
                const val BASE_URL = "${if (isReleaseBuild) releaseBaseUrl else debugBaseUrl}"
            }
            """.trimIndent()
        )
    }
}

tasks.named("preBuild") {
    dependsOn("generateBuildConfig")
}

tasks.named("generateBuildConfig").configure {
    outputs.upToDateWhen { false }
}


