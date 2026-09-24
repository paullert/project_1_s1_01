// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false

    id("androidx.room3") version "3.0.2" apply false

    id("dev.detekt") version "2.0.0-alpha.6" apply false
    id("com.github.spotbugs") version "6.5.11" apply false
    id("net.ltgt.errorprone") version "5.1.1" apply false
    id("pmd")
}

