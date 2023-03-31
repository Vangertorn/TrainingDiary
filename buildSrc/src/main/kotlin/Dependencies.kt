object Dependencies {

    object Test {
        const val testJunit = "junit:junit:4.13.2"
        const val testExtension = "androidx.test.ext:junit:1.1.3"
        const val testEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Plugins {
        const val androidGradlePlugin = "com.android.tools.build:gradle:7.4.1"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
        const val saveArgsGradlePlugin =
            "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2"
    }

    object Navigation {
        private const val version = "2.4.2"

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Kotlin {
        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:1.8.0"
    }

    object KotlinX{
        const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
    }

    object Hilt{
        private const val version = "2.38.1"

        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }
}