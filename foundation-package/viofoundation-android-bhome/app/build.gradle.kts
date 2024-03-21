import org.jetbrains.kotlin.storage.CacheResetOnProcessCanceled.enabled

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.vioside.viofoundation"
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.annotation:annotation:1.1.0")
    val kotlinVersion = "1.3.61"
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.android.support:multidex:1.0.3")
    testImplementation("junit:junit:4.12")

    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.2.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.android.support:multidex:1.0.3")
    implementation("androidx.fragment:fragment:1.2.5")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.1")
    implementation("com.squareup.moshi:moshi-kotlin-codegen:1.9.1")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2")

    val koin_version = "2.1.5"
    implementation("org.koin:koin-android:$koin_version")
    implementation("org.koin:koin-android-viewmodel:$koin_version")
    implementation("org.koin:koin-core-ext:$koin_version")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    testImplementation("org.koin:koin-test:$koin_version")


    implementation(project(":viofoundation"))
}