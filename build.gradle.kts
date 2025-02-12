// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) version "8.7.3" apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
    id("org.jetbrains.dokka") version "2.0.0"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(file("$rootDir/docs/KDoc"))
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(true)
            reportUndocumented.set(true)
            sourceRoots.setFrom(
                files(
                    "src/main/java/com/icjardinapps/dm2/durango",
                    "src/main/java/com/icjardinapps/dm2/durango/actividades",
                    "src/main/java/com/icjardinapps/dm2/durango/db"
                )
            )
        }
    }
}