plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish-android")
}

android {
    compileSdk = Versions.androidCompileSdk
    buildToolsVersion = Versions.androidBuildToolsVersion

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion  = Versions.compose
    }
}
