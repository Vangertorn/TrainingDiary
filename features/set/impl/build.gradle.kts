plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33
    namespace  = "com.yankin.trainingdiary.set.impl"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(project(":external:storage"))
    implementation(project(":external:preferences"))
    implementation(project(":features:set:api"))
    implementation(project(":core:coroutine"))

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    kapt(Dependencies.Room.compiler)
    implementation(Dependencies.Room.room)
    implementation(Dependencies.Room.kotlinExtensions)
}