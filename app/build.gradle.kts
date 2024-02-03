plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 34
    namespace = Config.applicationId
    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
}

dependencies {

    implementation(project(":external:storage"))
    implementation(project(":features:muscle_groups:api"))
    implementation(project(":features:muscle_groups:impl"))
    implementation(project(":core:coroutine"))
    implementation(project(":features:exercise_name:api"))
    implementation(project(":features:exercise_name:impl"))
    implementation(project(":features:training:api"))
    implementation(project(":features:training:impl"))
    implementation(project(":features:approach:api"))
    implementation(project(":features:approach:impl"))
    implementation(project(":external:preferences"))
    implementation(project(":features:exercise:api"))
    implementation(project(":features:exercise:impl"))
    implementation(project(":features:super_set:api"))
    implementation(project(":features:super_set:impl"))
    implementation(project(":screens:training_list:api"))
    implementation(project(":screens:training_list:impl"))
    implementation(project(":screens:training_create:api"))
    implementation(project(":screens:training_create:impl"))
    implementation(project(":screens:settings:api"))
    implementation(project(":screens:settings:impl"))
    implementation(project(":screens:season_ticket:api"))
    implementation(project(":screens:season_ticket:impl"))
    implementation(project(":screens:approach_create:api"))
    implementation(project(":screens:approach_create:impl"))
    implementation(project(":screens:exercise_create:api"))
    implementation(project(":screens:exercise_create:impl"))
    implementation(project(":screens:exercise_list:api"))
    implementation(project(":screens:exercise_list:impl"))
    implementation(project(":navigation"))
    implementation(project(":screens:common"))
    implementation(project(":features:theme:api"))
    implementation(project(":features:theme:impl"))
    implementation(project(":features:membership:api"))
    implementation(project(":features:membership:impl"))

    testImplementation(Dependencies.Test.testJunit)
    androidTestImplementation(Dependencies.Test.testExtension)
    androidTestImplementation(Dependencies.Test.testEspresso)

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.compiler)

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation(Dependencies.KotlinX.coroutineCore)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)


    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    implementation("androidx.fragment:fragment-ktx:1.6.2")
}

kapt {
    correctErrorTypes = true
}
