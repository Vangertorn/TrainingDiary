plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
}

android {
    compileSdk = 33
    namespace  = "com.yankin.trainingdiary.clear_delete_queue.impl"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdk = Config.minSdk
    }
}

dependencies {


    implementation(project(":core:coroutine"))
    implementation(project(":core:kotlin"))
    implementation(project(":features:training:api"))
    implementation(project(":features:training_block:api"))
    implementation(project(":workers:clear_delete_queue:api"))
    implementation(project(":core:resource_manager:api"))

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    implementation(Dependencies.Worker.worker)
}