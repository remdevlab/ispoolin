plugins {
    alias(libs.plugins.kotlin.jvm)
}

version = "1.2.3"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

group = "org.remdev.android"
extra["publishArtifactId"] = "ispoolin"

apply(from = rootProject.file("gradle/publish-github-jar.gradle.kts"))
