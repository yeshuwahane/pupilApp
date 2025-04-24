plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.yeshuwahane.pupil"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.yeshuwahane.pupil"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //navigatoin
    implementation("androidx.navigation:navigation-compose:2.7.6")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")

    //icons
    implementation("androidx.compose.material:material-icons-extended:1.7.8")


    // viewpager
    implementation("com.google.accompanist:accompanist-pager:0.32.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.32.0")

    // Logger
    debugImplementation("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:4.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")


    //coil
    implementation("io.coil-kt.coil3:coil-compose:3.0.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.3")
    implementation("io.coil-kt.coil3:coil-gif:3.0.4")


    // Retrofit and OkHttp for network calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Room Database
    implementation("androidx.room:room-runtime:2.7.0")
    implementation("androidx.room:room-ktx:2.7.0")
//    ksp("androidx.room:room-compiler:2.6.1")

// With this
    kapt("androidx.room:room-compiler:2.7.0")


    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    // Navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coroutine support
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")


    //


    // MediaPipe Tasks
    implementation("com.google.mediapipe:tasks-vision:0.20230731")

    // CameraX (Live feed)
    implementation("androidx.camera:camera-core:1.4.2")
    implementation("androidx.camera:camera-camera2:1.4.2")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.4.2")
    implementation("androidx.lifecycle:lifecycle-process:2.8.7") // Or match your other lifecycle version

//    implementation("com.google.mediapipe:solution-core:0.10.20")


}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}