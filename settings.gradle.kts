pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/remdevlab/ispoolin")
            credentials {
                username = providers.gradleProperty("GITHUB_USER_NAME").orElse("").get()
                password = providers.gradleProperty("GITHUB_USER_KEY").orElse("").get()
            }
        }
    }
}

rootProject.name = "IspoolinSample"
include(":prefz", ":ispoolin")
