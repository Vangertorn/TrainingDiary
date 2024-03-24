plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    implementation(Dependencies.KotlinX.coroutineCore)
    implementation(project(":core:date"))
    implementation(project(":features:muscle_groups:api"))
}