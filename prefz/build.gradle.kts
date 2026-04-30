plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "org.remdev.android.prefz"
    compileSdk = 35

    defaultConfig {
        minSdk = 16
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

version = "1.0.0"
group = "org.remdev.android"
extra["publishArtifactId"] = "prefz"

dependencies {
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
}

apply(from = rootProject.file("gradle/publish-github.gradle.kts"))
