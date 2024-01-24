plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = 33
    namespace  = "com.yankin.trainingdiary.approach_create.impl"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdk = Config.minSdk
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    implementation(project(":core:coroutine"))
    implementation(project(":external:preferences"))
    implementation(project(":navigation"))
    implementation(project(":features:training:api"))
    implementation(project(":features:muscle_groups:api"))
    implementation(project(":features:exercise_name:api"))
    implementation(project(":screens:common"))
    implementation(project(":screens:approach_create:api"))

    implementation(Dependencies.Hilt.android)
    implementation(project(":features:approach:api"))
    implementation(project(":features:exercise:api"))
    kapt(Dependencies.Hilt.compiler)

    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)
    implementation(Dependencies.AppCompatUi.appCompat)
    implementation(Dependencies.AppCompatUi.material)
    implementation(Dependencies.AppCompatUi.constraintlayout)

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
}