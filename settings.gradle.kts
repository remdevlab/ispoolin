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
                username = providers.gradleProperty("GITHUB_ACTOR").getOrElse("")
                password = providers.gradleProperty("GITHUB_TOKEN").getOrElse("")
            }
        }
    }
}

rootProject.name = "IspoolinSample"
include(":prefz", ":ispoolin")
