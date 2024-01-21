plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlin-android")
}

android {
    compileSdk = 33
    namespace  = "com.yankin.navigation"
    defaultConfig {
        minSdk = Config.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(Dependencies.KotlinX.coroutineCore)
    implementation(Dependencies.Navigation.navigationFragment)
}