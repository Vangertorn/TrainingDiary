plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33
    namespace  = "com.yankin.trainingdiary.preferences"
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildTypes {

            debug {
                buildConfigField("String", "preferences", "\"com.yankin.trainingdiary\"")
            }

            release {
                buildConfigField("String", "preferences", "\"com.yankin.trainingdiary\"")
            }
    }
}

dependencies {

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    kapt(Dependencies.Room.compiler)
    implementation(Dependencies.Room.room)
    implementation(Dependencies.Room.kotlinExtensions)
}