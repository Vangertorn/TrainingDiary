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
    namespace  = "com.yankin.trainingdiary.training_list.impl"
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
    implementation(project(":core:date"))
    implementation(project(":external:preferences"))
    implementation(project(":navigation"))
    implementation(project(":screens:common"))
    implementation(project(":features:training:api"))
    implementation(project(":features:muscle_groups:api"))
    implementation(project(":features:membership:api"))
    implementation(project(":screens:training_list:api"))
    implementation(project(":screens:training_create:api"))
    implementation(project(":screens:settings:api"))
    implementation(project(":screens:membership:api"))
    implementation(project(":screens:training_exercises:api"))

    implementation(Dependencies.Hilt.android)
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