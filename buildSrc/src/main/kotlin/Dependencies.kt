object Dependencies {

    object Test {
        const val testJunit = "junit:junit:4.13.2"
        const val testExtension = "androidx.test.ext:junit:1.1.3"
        const val testEspresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    object Plugins {
        const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.3"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"
        const val saveArgsGradlePlugin =
            "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2"
    }

    object Navigation {
        private const val version = "2.4.2"

        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$version"
    }
}