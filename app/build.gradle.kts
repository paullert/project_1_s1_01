import com.android.build.api.variant.BuildConfigField
import org.gradle.kotlin.dsl.android
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("androidx.room3")
}

android {
    namespace = "com.example.cst438_team1_project1"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.cst438_team1_project1"
        minSdk = 32
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        // Allows local variables to be used in program
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.room3.common)
    implementation(libs.androidx.room3.runtime)
    implementation(libs.androidx.ui.test.junit4)
    implementation(libs.core.ktx)
    ksp(libs.androidx.room3.compiler)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.foundation.layout)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("androidx.datastore:datastore-preferences:1.2.1")

    //Allows the use of internet images in composable
    implementation("io.coil-kt.coil3:coil-compose:3.4.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.4.0")

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.core.ktx)
    testImplementation(libs.kotlinx.coroutines.test)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // TODO: ADDRESS LATER
    androidTestImplementation(libs.androidx.room3.testing)
    implementation(libs.androidx.sqlite.bundled)
    testImplementation(libs.androidx.sqlite.jvm.bundled)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

//Below Code is used to populate app with API Key
// Must provide the api key in the secret.properties file
val secrets = Properties()
val secretsFile = rootProject.file("secrets.properties")

if (secretsFile.exists()) {
    secretsFile.inputStream().use { secrets.load(it) }
}

val apiKey = secrets.getProperty("COINGECKO_API_KEY")
    ?: System.getenv("COINGECKO_API_KEY")
    ?: error("COINGECKO_API_KEY is missing")

androidComponents.onVariants { variant ->
    variant.buildConfigFields?.put(
        "COINGECKO_API_KEY",
        BuildConfigField(
            type = "String",
            value = "\"$apiKey\"",
            comment = "API key"
        )
    )
}
