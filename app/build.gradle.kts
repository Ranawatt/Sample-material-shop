plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

val composeVersion = "0.1.0-dev16"
val coroutinesVersion = "1.3.7"
val roomVersion = "2.2.5"
val archLifecycleVersion = "2.2.0"
val filamentVersion = "1.8.0"

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("com.google.android.material:material:1.2.0")

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")

    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")

    implementation("androidx.lifecycle:lifecycle-extensions:$archLifecycleVersion")
    kapt("androidx.lifecycle:lifecycle-common-java8:$archLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$archLifecycleVersion")

    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.foundation:foundation-layout:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.material:material-icons-extended:$composeVersion")
    implementation("androidx.compose.runtime:runtime:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.ui:ui-tooling:$composeVersion")

    implementation("com.google.android.filament:filament-android:$filamentVersion")
    implementation("com.google.android.filament:filament-utils-android:$filamentVersion")
    implementation("com.google.android.filament:gltfio-android:$filamentVersion")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.3"

    defaultConfig {
        applicationId = "com.curiouscreature.compose"
        minSdkVersion(29)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.incremental", "true")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerVersion = "1.4.0-rc"
        kotlinCompilerExtensionVersion = composeVersion
    }

    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }

    aaptOptions {
        noCompress("filamat", "ktx", "glb")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check", "-Xskip-metadata-version-check")
    }
}

//apply plugin: 'com.android.application'
//apply plugin: 'kotlin-android'
//apply plugin: 'kotlin-kapt'
//apply plugin: 'kotlin-android-extensions'
//
//android {
//    compileSdkVersion 29
//    buildToolsVersion "29.0.3"
//
//    defaultConfig {
//        applicationId "com.example.sample_material_shop"
//        minSdkVersion 25
//        targetSdkVersion 29
//        versionCode 1
//        versionName "1.0"
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//
//        javaCompileOptions {
//            annotationProcessorOptions {
//                argument("room.incremental", "true")
//            }
//        }
//    }
//
//    buildTypes {
//        release {
//            minifyEnabled false
//            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
//        }
//    }
//
//    sourceSets["main"].java.srcDir("src/main/kotlin")
//    compileOptions {
//        sourceCompatibility "1.8"
//        targetCompatibility "1.8"
//    }
//
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8.toString()
//    }
//
//    buildFeatures {
//        compose = true
//    }
//
//    composeOptions {
//        kotlinCompilerVersion = "1.4.0-rc"
//        kotlinCompilerExtensionVersion = "0.1.0-dev16"
//    }
//
//    packagingOptions {
//        exclude("META-INF/atomicfu.kotlin_module")
//    }
//
//    aaptOptions {
//        noCompress("filamat", "ktx", "glb")
//    }
//}
//
//
//dependencies {
//    implementation fileTree(dir: "libs", include: ["*.jar"])
//    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
//    api "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
//    api "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.7"
//
//    implementation 'com.google.android.material:material:1.2.0'
//    implementation 'androidx.core:core-ktx:1.3.1'
//    implementation 'androidx.appcompat:appcompat:1.3.0-alpha01'
//    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
//
//    implementation "androidx.room:room-runtime:2.2.5"
//    implementation "androidx.room:room-ktx:2.2.5"
//    implementation 'androidx.compose:compose-runtime:0.1.0-dev14'
//    kapt "androidx.room:room-compiler:2.2.5"
//    annotationProcessor "androidx.room:room-compiler:2.2.5"
//
//    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
//    kapt "androidx.lifecycle:lifecycle-common-java8:2.2.0"
//    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
//
//    implementation 'androidx.compose.animation:animation:0.1.0-dev16'
//    implementation 'androidx.compose.foundation:foundation:0.1.0-dev16'
//    implementation 'androidx.compose.foundation:foundation-layout:0.1.0-dev16'
//    implementation 'androidx.compose.material:material:0.1.0-dev16'
//    implementation 'androidx.compose.material:material-icons-extended:0.1.0-dev16'
//    implementation 'androidx.compose.runtime:runtime:0.1.0-dev16'
//    implementation 'androidx.compose.runtime:runtime-livedata:0.1.0-dev16'
//    implementation 'androidx.compose.ui:ui:0.1.0-dev16'
//    implementation 'androidx.ui:ui-tooling:0.1.0-dev16'
//
//    implementation "com.google.android.filament:filament-android:1.8.0"
//    implementation "com.google.android.filament:filament-utils-android:1.8.0"
//    implementation "com.google.android.filament:gltfio-android:1.8.0"
//
//    testImplementation 'junit:junit:4.13'
//    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
//    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
//
//}