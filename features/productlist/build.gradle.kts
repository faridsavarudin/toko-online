plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "productlist"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)

            implementation(libs.paging.cashapp.common)
            implementation(libs.paging.cashapp.compose)

            implementation(projects.libraries.core)
            implementation(projects.libraries.component)
            implementation(projects.apis.product)
            //put your multiplatform dependencies here
        }
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.paging.androidx.runtime)
            implementation(libs.paging.androidx.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "id.alpha.features.productlist"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
