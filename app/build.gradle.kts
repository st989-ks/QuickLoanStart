plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles (
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "$project.rootDir/proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"

        @Suppress("SuspiciousCollectionReassignment")
        freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    packagingOptions {
        resources.excludes.add("META-INF/NOTICE")
        resources.excludes.add("META-INF/notice.txt")
        resources.excludes.add("META-INF/NOTICE.txt")
        resources.excludes.add("META-INF/license.txt")
        resources.excludes.add("META-INF/LICENSE.txt")
        resources.excludes.add("META-INF/LICENSE")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
        resources.excludes.add("META-INF/LGPL-3")
        resources.excludes.add("META-INF/W3C-TEST")
        resources.excludes.add("META-INF/DEPENDENCIES")
        resources.excludes.add("META-INF/main.kotlin_module")
        resources.excludes.add("**/*.exclude")
        resources.excludes.add("META-INF/*")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    kapt {
        // Allow references to generated code
        correctErrorTypes = true
        useBuildCache = true
    }
}

dependencies {

    // Kotlin
    implementation(KotlinX.Coroutines.core)
    implementation(KotlinX.Coroutines.android)
    implementation(KotlinX.Json.json)

    //Google
    implementation(Google.Material.material)

    // Lifecycle
    implementation(AndroidX.Lifecycle.livedata)
    implementation(AndroidX.Lifecycle.viewmodel)

    // Android Work Manager
    implementation(AndroidX.AndroidWorkManager.workManager)
    implementation(AndroidX.AndroidLifecycleWorkManagerHilt.hiltWork)
    kapt(AndroidX.AndroidLifecycleWorkManagerHilt.hiltCompiler)

    // Fragment
    implementation(AndroidX.Fragment.fragment)

    // Activity
    implementation(AndroidX.Activity.activity)

    // Core
    implementation(AndroidX.Core.core)

    // Appcompat
    implementation(AndroidX.Appcompat.appcompat)

    // ConstraintLayout
    implementation(AndroidX.ConstraintLayout.constraintLayout)

    // Hilt
    implementation(HiltDagger.android)
    kapt(HiltDagger.compiler)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.retrofitConverterGson)
    implementation(Retrofit.retrofitConverterScalars)

    // Navigation
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    // Test
    testImplementation(Test.Junit.jupiter)
    testImplementation(Test.Mockito.mockitoinline)
    testImplementation(Test.Mockito.mockitoKotlinMockito)
    testImplementation(Test.Androidx.arch)
    testImplementation(Test.Coroutines.test)
    testImplementation(Test.Junit.junit)
    testImplementation(Test.Mockito.mockitoMockito)


    androidTestImplementation(Test.Junit.junit)
    androidTestImplementation(Test.Junit.jupiter)
    androidTestImplementation(Test.Androidx.espressoCore)
    androidTestImplementation(Test.Androidx.junitEtx)
    androidTestImplementation(Test.Androidx.fragmentTesting)
    androidTestImplementation(Test.Kaspersky.kaspresso)
//    testImplementation(Test.Mockito.mockitoAndroid)
//    testImplementation(Test.Androidx.core)
//    testImplementation(Test.Androidx.junitEtx)
//    testImplementation(Test.Androidx.rules)

    configurations.configureEach {
        resolutionStrategy {
            force(Test.Junit.junit)
        }
    }
}
