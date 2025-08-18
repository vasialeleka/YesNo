import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization")
    id("dev.icerock.mobile.multiplatform-resources") version "0.25.0"
    id("com.google.gms.google-services")

    }

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(project.dependencies.platform(libs.android.firebase.bom))
            implementation("dev.icerock.moko:resources-compose:0.23.0")
        }
        commonMain.dependencies {
            val voyagerVersion = "1.1.0-beta02"
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlinx.serialization.json)
            implementation(libs.gitlive.firebase.firestore)
            api("dev.icerock.moko:resources:0.25.0")
            implementation("dev.icerock.moko:resources-compose:0.25.0")
            implementation("cafe.adriel.voyager:voyager-navigator:${voyagerVersion}")    /*        implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-beta19")
            implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:1.0.0-beta19") */
            implementation("cafe.adriel.voyager:voyager-lifecycle-kmp:${voyagerVersion}")
            implementation("cafe.adriel.voyager:voyager-screenmodel:${voyagerVersion}")
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("cafe.adriel.voyager:voyager-koin:${voyagerVersion}")// якщо будеш використовувати Koin
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}



android {
    namespace = "com.game.yes"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.game.yes"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }

}

multiplatformResources {
    resourcesPackage.set("com.game.yes")
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.game.yes"
            packageVersion = "1.0.0"
        }
    }
}
