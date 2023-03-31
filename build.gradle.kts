buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.Plugins.androidGradlePlugin)
        classpath(Dependencies.Plugins.kotlinGradlePlugin)
        classpath(Dependencies.Plugins.saveArgsGradlePlugin)
        classpath(Dependencies.Hilt.plugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("org.jmailen.kotlinter") version ("3.4.4")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

    apply{
        plugin("org.jmailen.kotlinter")
    }
}