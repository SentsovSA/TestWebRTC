import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.compose)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        jvmToolchain(17)
    }
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    version = "1.0"
    /*listOf(
        iosX64(),
        iosArm64()
        //iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "app"
            isStatic = true
        }
    }*/

    cocoapods {
        summary = "Test WebRTC"
        homepage = "https://example.com"
        version = "1.0"
        ios.deploymentTarget = "14.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "app"
            isStatic = true
            //export(project(":app-core"))
            //@OptIn(ExperimentalKotlinGradlePluginApi::class)
            //transitiveExport = true
            binaryOption("bundleId", "group.ost.paymentservice")
            //linkerOpts.add("-lsqlite3")
            //if (System.getenv("XCODE_VERSION_MAJOR") == "1500") {
                //linkerOpts("-ld64")
            //}
        }

        //extraSpecAttributes["resources"] = "['src/commonMain/composeResources/**']"
    }
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.RequiresOptIn")
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
                optIn("androidx.compose.material.ExperimentalMaterialApi")
                optIn("androidx.compose.foundation.ExperimentalFoundationApi")
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            }
        }
        commonMain {
            resources.srcDirs("/Users/user/StudioProjects/payment-service/app/src/commonMain/composeResources")
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.material3)
                //implementation(libs.compose.fontawesome.icons)
                implementation(compose.components.resources)
                implementation(libs.composeIcons.fontAwesome)

                implementation(libs.camera.image.picker.ui)
                implementation(libs.camera.image.picker)
                /*implementation(libs.file.picker)*/

                implementation(libs.shimmer)
                implementation(libs.constraintlayout)
                //implementation(libs.koin.compose)

                implementation(libs.mlkit.barcode.scanning)

                implementation(project(":app-webview"))
                implementation(project(":app-camera"))

                implementation(project(":app-viewmodels"))
                implementation(project(":app-domain"))
                implementation(project(":app-core"))
                implementation(project(":app-filepicker"))
                implementation(project(":app-context"))

                implementation(libs.androidx.lifecycle.common)
                implementation(libs.androidx.lifecycle.runtime)
                //implementation(libs.androidx.lifecycle.viewModel)
                implementation(libs.coil3)
                implementation(libs.cupertino)
                implementation(libs.cupertino.native)
                implementation(libs.cupertino.adaptive)
                implementation(libs.cupertino.decompose)
                implementation(libs.cupertino.icons.extended)

                implementation(libs.coil.network.ktor3)
                implementation(libs.napier)

                implementation(libs.kotlinx.datetime)

                implementation(libs.sonner.v038)
                implementation(libs.kamel.image.default)
                /*api("io.github.qdsfdhvh:image-loader:1.8.3")*/
            }
        }
        androidMain {
            dependencies {
                implementation(project.dependencies.platform(libs.androidx.compose.bom))
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.compose.ui)
                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation(libs.kotlinx.coroutines.core)
                implementation("com.google.android.play:app-update:2.1.0")
                implementation("com.google.android.play:app-update-ktx:2.1.0")
                implementation(libs.exoplayer)
                implementation(libs.exoplayer.session)
                implementation(libs.exoplayer.ui)
                implementation(libs.exoplayer.dash)
                //implementation(libs.androidx.lifecycle.runtime.compose)
                //implementation(libs.androidx.lifecycle.viewmodel.compose)
            }
        }
        iosMain{
            dependencies{

            }
        }
    }
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        /*freeCompilerArgs = listOf(
            "-opt-in=" + listOf(
                "kotlin.RequiresOptIn",
                "org.jetbrains.compose.resources.ExperimentalResourceApi"
            ).joinToString(",")
        )*/
    }
}

android {
    namespace = "test.webrtc.test"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets {
        getByName("main") {
            manifest {
                srcFile("src/androidMain/AndroidManifest.xml")
            }
            res {
                srcDirs("src/androidMain/res")
            }
            /*resources {
                srcDirs("src/commonMain/resources")
            }*/
        }
    }
    defaultConfig {
        applicationId = "test.webrtc.test"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 100421
        versionName = "1.4.2"
    }
    applicationVariants.all {
        val variant = this
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            .withZone(ZoneId.systemDefault())
        val time = formatter.format(Instant.now())
        variant.outputs.map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }.forEach { output ->
            val outputFileName = "${variant.applicationId}-${variant.baseName}-${variant.versionName}-${variant.versionCode}-$time.apk"
            output.outputFileName = outputFileName
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            firebaseCrashlytics.mappingFileUploadEnabled = true
            manifestPlaceholders["enableCrashlytics"] = true
        }
        debug {
            manifestPlaceholders["enableCrashlytics"] = false
            firebaseCrashlytics.mappingFileUploadEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    dependencies {
        debugImplementation(libs.androidx.compose.ui.tooling)
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "test.webrtc.test.resources"
    generateResClass = always
}

gradle.taskGraph.whenReady {
    tasks.findByName("init")?.mustRunAfter(tasks.named("generateComposeResClass"))
}

tasks.named("generateComposeResClass").configure {
    outputs.upToDateWhen { false }
}