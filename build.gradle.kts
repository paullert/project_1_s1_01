// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    id("androidx.room3") version "3.0.2" apply false
    id("com.github.spotbugs") version "6.5.11"
    checkstyle
    pmd
    id("net.ltgt.errorprone") version "5.1.1"
}

pmd {
    toolVersion = "7.19.0"
//    ruleSetFiles = files("custom-pmd-ruleset.xml")
//    ruleSets = intArrayOf()
    isIgnoreFailures = false
}

checkstyle {
    toolVersion = "10.12.4"
}