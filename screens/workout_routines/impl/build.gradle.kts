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
    namespace  = "com.yankin.trainingdiary.exercise_list.impl"
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
    implementation(project(":core:kotlin"))
    implementation(project(":core:date"))
    implementation(project(":external:preferences"))
    implementation(project(":navigation"))
    implementation(project(":features:training:api"))
    implementation(project(":features:muscle_groups:api"))
    implementation(project(":features:exercise_pattern:api"))
    implementation(project(":features:exercise:api"))
    implementation(project(":features:training_block:api"))
    implementation(project(":features:set:api"))
    implementation(project(":screens:common"))
    implementation(project(":screens:workout_routines:api"))
    implementation(project(":screens:exercise_create:api"))
    implementation(project(":screens:training_create:api"))
    implementation(project(":screens:set_create:api"))
    implementation(project(":core:resource_manager:api"))

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)
    implementation(Dependencies.AppCompatUi.appCompat)
    implementation(Dependencies.AppCompatUi.material)
    implementation(Dependencies.AppCompatUi.constraintlayout)
    implementation(Dependencies.AdapterDelegates.adapter)
}