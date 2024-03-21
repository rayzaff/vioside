import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(19)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

}

publishing {

    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.vioside"
                artifactId = "viofoundation"
                version = "0.3.16"
                artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
                pom {
                    withXml {
                        // add dependencies to pom
                        val dependencies = asNode().appendNode("dependencies")
                        configurations.implementation.get().dependencies.forEach {
                            if (it.group != null &&
                                "unspecified" != it.name &&
                                it.version != null) {

                                val dependencyNode = dependencies.appendNode("dependency")
                                dependencyNode.appendNode("groupId", it.group)
                                dependencyNode.appendNode("artifactId", it.name)
                                dependencyNode.appendNode("version", it.version)
                            }
                        }
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/vioside/viofoundation-android") // Github Package
            credentials {
                //Fetch these details from the properties file or from Environment variables
                username = gradleLocalProperties(rootDir).getProperty("gpr.usr") ?: System.getenv("GPR_USER")
                password = gradleLocalProperties(rootDir).getProperty("gpr.key") ?: System.getenv("GPR_API_KEY")
            }
        }
    }

}

dependencies {

    val koinVersion = "2.1.5"
    val kotlinVersion = "1.3.61"
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("com.kaopiz:kprogresshud:1.2.0")
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.squareup.moshi:moshi-kotlin:1.9.1")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.6.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.2")
    implementation("com.squareup.okhttp3:okhttp:4.2.1")
    implementation("com.github.bumptech.glide:glide:4.11.0")

    implementation("org.koin:koin-android:$koinVersion")
    implementation("org.koin:koin-android-viewmodel:$koinVersion")
    implementation("org.koin:koin-core-ext:$koinVersion")

    testImplementation("junit:junit:4.12")
    testImplementation("org.koin:koin-test:$koinVersion")
    testImplementation("androidx.test:core:1.2.0")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")
    testImplementation("android.arch.core:core-testing:1.1.1")
}
